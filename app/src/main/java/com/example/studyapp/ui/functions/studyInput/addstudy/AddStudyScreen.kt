package com.example.studyapp.ui.functions.studyInput.addstudy

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyapp.R
import com.example.studyapp.data.AppRepository
import com.example.studyapp.data.ChoiceAnswerEntity
import com.example.studyapp.data.CompletionAnswerEntity
import com.example.studyapp.data.PairAnswerEntity
import com.example.studyapp.data.QuestionEntity
import com.example.studyapp.data.QuestionWithAnswers
import com.example.studyapp.data.StartStudyViewModel
import com.example.studyapp.data.StartStudyViewModelProvider
import com.example.studyapp.data.StudyRecords
import com.example.studyapp.data.StudyTimes
import com.example.studyapp.data.Subjects
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.OutlinedButton
import com.example.studyapp.ui.functions.studyInput.parts.SelectedSubjectDropdown
import com.example.studyapp.ui.functions.studyInput.parts.TimeInput
import com.example.studyapp.ui.functions.studyInput.parts.TitleInput
import com.example.studyapp.ui.navigation.NavigationDestination
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

object AddStudyDestinations : NavigationDestination {
    override val route = "addStudy"
    override val titleRes = R.string.entry_addStudy
}

@Composable
fun AddStudyScreen(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    navigateToHome: () -> Unit = {}
) {
    val StartStudyUiState by viewModel.startStudyUiState.collectAsState()

    if (StartStudyUiState.isLoading) {
        CircularProgressIndicator() // ローディング中はインジケーターを表示
    } else {
        val subjectItems = StartStudyUiState.subjectList.collectAsState(initial = listOf()).value

        LaunchedEffect(subjectItems) {
            Log.d("StartStudyScreen", "Subjects in UI: $subjectItems")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AddStudyBody(
                subjectItems = subjectItems,
                navigateToHome = navigateToHome
            )
        }
    }
}

@Composable
fun AddStudyBody(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    subjectItems: List<Subjects> = listOf(),
    navigateToHome: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    var selectedSubject by remember {
        mutableStateOf(Subjects(1, "math", R.drawable.function, 0xff8AB4F8, 0xff394557))
    }
    var title by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf(LocalDateTime.now()) }
    var endTime by remember { mutableStateOf(LocalDateTime.now()) }
    var description by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var isStartTime by remember { mutableStateOf(true) }

    LaunchedEffect(selectedSubject) {
        if (subjectItems.isNotEmpty() && selectedSubject !in subjectItems) {
            selectedSubject = subjectItems.first()
        }
    }

    StudyAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp

            Box(
                modifier = Modifier
                    .offset(x = (screenWidth - 364.dp) / 2, y = 60.dp)
                    .zIndex(1f)
            )
            {
                SelectedSubjectDropdown(
                    subjectItems = subjectItems,
                    selectedSubject = selectedSubject,
                    onSubjectSelected = { newSubject ->
                        selectedSubject = newSubject
                    }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Spacer(modifier = Modifier.height(70.dp))
                Spacer(modifier = Modifier.height(30.dp))
                TitleInput(
                    onInputChanged = { newTitle ->
                        title = newTitle
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.width(364.dp)
                ) {
                    TimeInput(
                        inputtedTime = startTime,
                        onMonthClicked = {
                            isStartTime = true
                            showDatePicker = true
                        },
                        onTimeClicked = {
                            isStartTime = true
                            showTimePicker = true
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.arrow_right_long),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color(0xff484848)),
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TimeInput(
                        inputtedTime = endTime,
                        onMonthClicked = {
                            isStartTime = false
                            showDatePicker = true
                        },
                        onTimeClicked = {
                            isStartTime = false
                            showTimePicker = true
                        }
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .width(364.dp)
                        .height(260.dp)
                        .clip(RoundedCornerShape(10))
                        .background(Color((0xff202020)))
                ) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { newDescription ->
                            description = newDescription
                        },
                        placeholder = {
                            Text(
                                text = "description",
                                color = Color(0xff525252)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedButton(
                    text = "Save",
                    color = Color(0xffDF9763),
                    buttonWidth = 175,
                    onClick = {
                        if (title != "") {
                            val studiedTime = Duration.between(startTime, endTime).seconds
                            if (studiedTime >= 0) {
                                coroutineScope.launch {
                                    viewModel.addStudyRecord(
                                        subjectId = selectedSubject.subjectId,
                                        title = title,
                                        description = null,
                                        studiedTime = studiedTime.toInt(),
                                        startStudyDate = startTime,
                                        finishStudyDate = endTime,
                                        afterMemo = description
                                    )
                                }
                                navigateToHome()
                            }
                        } else{
                            Log.d("StartStudyBody", "Title is empty")
                        }
                    }
                )
            }
        }

        if (showDatePicker) {
            val context = LocalContext.current
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    val newDate = startTime.withYear(year).withMonth(month + 1).withDayOfMonth(day)
                    if (isStartTime) startTime = newDate else endTime = newDate
                    showDatePicker = false
                },
                startTime.year,
                startTime.monthValue - 1,
                startTime.dayOfMonth
            ).show()
        }

        if (showTimePicker) {
            val context = LocalContext.current
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    val newTime = startTime.withHour(hour).withMinute(minute)
                    if (isStartTime) startTime = newTime else endTime = newTime
                    showTimePicker = false
                },
                startTime.hour,
                startTime.minute,
                true
            ).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddStudyScreenPreview() {
    val mockRepository = object : AppRepository {
        override fun getQuestionWithAnswers(vocabularyId: Int): Flow<List<QuestionWithAnswers>> {
            return MutableStateFlow(
                listOf(
                    QuestionWithAnswers(
                        question = QuestionEntity(
                            questionId = 1,
                            vocabularyId = 1,
                            questionType = "pair",
                            questionText = "What is the capital of France?",
                            isLiked = true,
                            correctedNumber = 0,
                            uncorrectedNumber = 0
                        ),
                        pairAnswer = PairAnswerEntity(questionId = 1, answerText = "Paris"),
                        completionAnswers = null,
                        choiceAnswer = null
                    ),
                    QuestionWithAnswers(
                        question = QuestionEntity(
                            questionId = 2,
                            vocabularyId = 1,
                            questionType = "completion",
                            questionText = "Complete the sentence: The sky is ___?",
                            isLiked = true,
                            correctedNumber = 0,
                            uncorrectedNumber = 0
                        ),
                        pairAnswer = null,
                        completionAnswers = CompletionAnswerEntity(questionId = 2, answerText = listOf("blue", "clear")),
                        choiceAnswer = null
                    ),
                    QuestionWithAnswers(
                        question = QuestionEntity(
                            questionId = 3,
                            vocabularyId = 1,
                            questionType = "choice",
                            questionText = "Choose the correct answer: 2+2 = ?",
                            isLiked = true,
                            correctedNumber = 0,
                            uncorrectedNumber = 0
                        ),
                        pairAnswer = null,
                        completionAnswers = null,
                        choiceAnswer = ChoiceAnswerEntity(
                            questionId = 3,
                            correctAnswer = "4",
                            choices = listOf("3", "4", "5")
                        )
                    )
                )
            )
        }

        override fun getAllVocabularies(): Flow<List<VocabularyEntity>> {
            return MutableStateFlow(
                listOf(
                    VocabularyEntity(vocabularyId = 1, title = "Math Basics", iconColor = 0xFF123456, description = "Basic Math Questions")
                )
            )
        }

        override suspend fun insertVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun deleteVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun deleteAllRelatedData(vocabularyId: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteQuestionAndAnswers(questionId: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun updateVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun insertQuestion(question: QuestionEntity): Int {
            TODO("Provide the return value")
        }

        override suspend fun updateQuestion(question: QuestionEntity) {}
        override suspend fun updateIsLiked(questionId: Int, isLiked: Boolean) {
            TODO("Not yet implemented")
        }

        override suspend fun insertPairAnswer(answer: PairAnswerEntity) {}
        override suspend fun updatePairAnswer(answer: PairAnswerEntity) {}
        override suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity) {}
        override suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity) {}
        override suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity) {}
        override suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity) {}
        override suspend fun updateUncorrectedNumber(questionId: Int, uncorrectedNumber: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun updateCorrectedNumber(questionId: Int, correctNumber: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun getSelectedSubjects(): Flow<List<Subjects>> {
            return MutableStateFlow(
                listOf(
                    Subjects(1, "math", R.drawable.function, 0xff8AB4F8, 0xff394557),
                    Subjects(2, "english", R.drawable.function, 0xff8AB4F8, 0xff394557),
                    Subjects(3, "japanese", R.drawable.function, 0xff8AB4F8, 0xff394557),
                    Subjects(4, "science", R.drawable.function, 0xff8AB4F8, 0xff394557)
                )
            )
        }

        override suspend fun getSubjectById(subjectId: Int): Subjects {
            TODO("Not yet implemented")
        }

        override suspend fun getStudyTimes(): Flow<List<StudyTimes>> {
            TODO("Not yet implemented")
        }

        override suspend fun insertStudyTime(studyTime: StudyTimes) {
            TODO("Not yet implemented")
        }

        override suspend fun getAllStudyRecords(): Flow<List<StudyRecords>> {
            TODO("Not yet implemented")
        }

        override suspend fun getStudyRecordById(studyRecordId: Int): StudyRecords {
            TODO("Not yet implemented")
        }

        override suspend fun insertStudyRecord(studyRecord: StudyRecords): Int {
            TODO("Not yet implemented")
        }

        override suspend fun updateStudyRecord(studyRecord: StudyRecords) {
            TODO("Not yet implemented")
        }
    }

    val viewModel = StartStudyViewModel(mockRepository)

    AddStudyBody(viewModel = viewModel)
}