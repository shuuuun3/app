package com.example.studyapp.ui.functions.timer

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TimerFinishScreen(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    timerType: String,
    subjectId: Int?,
    studyRecordId: Int,
    navigateToHome: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    var studyRecordItem by remember { mutableStateOf<StudyRecords?>(null) }
    var subjectItem by remember { mutableStateOf<Subjects?>(null) }
    var review by remember { mutableStateOf("") }

    LaunchedEffect(subjectId) {
        studyRecordItem = viewModel.getStudyRecordById(studyRecordId)
        if (timerType == "startStudy" && subjectId != null) {
            subjectItem = viewModel.getSubjectsById(subjectId)
        }
    }

    StudyAppTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Well done!",
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(40.dp))
            if (timerType == "startStudy" && subjectItem != null) {
                subjectItem?.let { subjectItem ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = subjectItem.imageId),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color(subjectItem.color)),
                            modifier = Modifier
                                .size(60.dp)
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            text = subjectItem.name,
                            fontSize = 40.sp,
                            color = Color(subjectItem.color),
                        )
                    }
                }
            }
            studyRecordItem?.let { record ->
                val formattedStartTimeMonth = record.startStudyDate.format(DateTimeFormatter.ofPattern("MM/dd"))
                val formattedStartTimeTime = record.startStudyDate.format(DateTimeFormatter.ofPattern("HH:mm"))
                val formattedFinishTimeMonth = record.finishStudyDate?.format(DateTimeFormatter.ofPattern("MM/dd"))
                val formattedFinishTimeTime = record.finishStudyDate?.format(DateTimeFormatter.ofPattern("HH:mm"))

                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(100))
                        .background(Color(0xff202020))
                ) {
                    Text(
                        text = record.title.toString(),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(150.dp)
                            .height(90.dp)
                            .clip(RoundedCornerShape(10))
                            .background(Color(0xff202020))
                    ) {
                        Text(text = formattedStartTimeMonth, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                        Text(text = formattedStartTimeTime, fontSize = 36.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                        modifier = Modifier
                            .size(45.dp)
                            .weight(1f),
                        colorFilter = ColorFilter.tint(Color(0xff484848))
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(150.dp)
                            .height(90.dp)
                            .clip(RoundedCornerShape(10))
                            .background(Color(0xff202020))
                    ) {
                        if (formattedFinishTimeMonth != null && formattedFinishTimeTime != null) {
                            Text(text = formattedFinishTimeMonth, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                            Text(text = formattedFinishTimeTime, fontSize = 36.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(10))
                        .background(Color((0xff202020)))
                ) {
                    OutlinedTextField(
                        value = review,
                        onValueChange = { newText ->
                            review = newText
                        },
                        placeholder = {
                            Text(
                                text = "review",
                                color = Color(0xff525252)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    text = "Save",
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateStudyRecord(
                                studyRecordId = studyRecordId,
                                afterMemo = review
                            )
                            navigateToHome()
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun TimerFinishScreenPreview() {
    val mockRepository= object : AppRepository {
        override fun getAllVocabularies(): Flow<List<VocabularyEntity>> {
            TODO("Not yet implemented")
        }

        override fun getQuestionWithAnswers(vocabularyId: Int): Flow<List<QuestionWithAnswers>> {
            TODO("Not yet implemented")
        }

        override suspend fun insertVocabulary(vocabulary: VocabularyEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteVocabulary(vocabulary: VocabularyEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteAllRelatedData(vocabularyId: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteQuestionAndAnswers(questionId: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun updateVocabulary(vocabulary: VocabularyEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun insertQuestion(question: QuestionEntity): Int {
            TODO("Not yet implemented")
        }

        override suspend fun updateQuestion(question: QuestionEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun updateIsLiked(questionId: Int, isLiked: Boolean) {
            TODO("Not yet implemented")
        }

        override suspend fun insertPairAnswer(answer: PairAnswerEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun updatePairAnswer(answer: PairAnswerEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun updateUncorrectedNumber(questionId: Int, uncorrectedNumber: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun updateCorrectedNumber(questionId: Int, correctNumber: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun getSelectedSubjects(): Flow<List<Subjects>> {
            TODO("Not yet implemented")
        }

        override suspend fun getSubjectById(subjectId: Int): Subjects {
            return if (subjectId == 1) {
                Subjects(1, "math", R.drawable.function, 0xff8AB4F8, 0xff8AB4F8)
            }else {
                Subjects(2, "english", R.drawable.earth, 0xff8AB4F8, 0xff8AB4F8)
            }
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

    TimerFinishScreen(viewModel = viewModel, timerType = "study", subjectId = 1, studyRecordId = 1)
}