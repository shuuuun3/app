package com.example.studyapp.ui.functions.startstudy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
import com.example.studyapp.data.SelectedSubjects
import com.example.studyapp.data.StartStudyViewModel
import com.example.studyapp.data.StartStudyViewModelProvider
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.navigation.NavigationDestination
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.flow.Flow

object StartStudyDestinations: NavigationDestination {
    override val route = "startStudy"
    override val titleRes = R.string.entry_startStudy
}

@Composable
fun StartStudyScreen(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory)
) {
    val StartStudyUiState by viewModel.startStudyUiState.collectAsState()
    val subjectItems = StartStudyUiState.subjectList.collectAsState(initial = listOf()).value
    StartStudyBody(
        subjectItems = subjectItems
    )
}


@Composable
fun StartStudyBody(
    subjectItems: List<SelectedSubjects> = listOf()
) {
    var selectedSubjectName by remember { mutableStateOf("noValue") }

    StudyAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                SelectedSubjectDropdown(
                    subjectItems = subjectItems,
                    onSubjectSelected = { selectedSubject ->
                        selectedSubjectName = selectedSubject.name
                    }
                )
                TitleInput()
            }
        }
    }
}

@Composable
fun TitleInput(
    onInputChanged: (String) -> Unit = {}
) {
    StudyAppTheme {
        var inputText by remember { mutableStateOf("") }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(364.dp)
                .height(70.dp)
                .background(Color(0xff202020), shape = RoundedCornerShape(100))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painterResource(R.drawable.textinput),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                    colorFilter = ColorFilter.tint(Color(0xff434343))
                )
                OutlinedTextField(
                    value = inputText,
                    placeholder = {
                        Text(
                            text = "title",
                            color = Color(0xff525252),
                            modifier = Modifier.padding(top = 2.dp, start = 100.dp)
                        )
                    },
                    onValueChange = { newText ->
                        inputText = newText
                        onInputChanged(newText)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleInputPreview() {
    TitleInput()
}

@Composable
fun SelectSubject(
    fontColor: Long,
    backgroundColor: Long,
    imageId: Int,
    onClick: () -> Unit = {},
    topRounded: Int = 100,
    bottomRounded: Int = 100,
    isExpanded: Boolean = false
) {
    var isSelectedSubject by remember { mutableStateOf("math") }

    val invisibleHeight = 0.dp
    val expandedHeight = 70.dp

    StudyAppTheme {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(364.dp)
                    .height(70.dp)
                    .background(
                        Color(backgroundColor), shape = RoundedCornerShape(
                            topStartPercent = topRounded,
                            topEndPercent = topRounded,
                            bottomStartPercent = bottomRounded,
                            bottomEndPercent = bottomRounded
                        )
                    )
                    .clickable { onClick() }
            ) {
                Text(
                    text = isSelectedSubject,
                    fontSize = 18.sp,
                    color = Color(fontColor)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        if (isExpanded) {
                            painterResource(id = R.drawable.arrow_up)
                        } else {
                            painterResource(id = R.drawable.arrow_down)
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        colorFilter = ColorFilter.tint(Color(fontColor))
                    )
                    Spacer(modifier = Modifier.width(70.dp))
                    Image(
                        painterResource(id = imageId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(27.dp),
                        colorFilter = ColorFilter.tint(Color(fontColor))
                    )
                }
            }
        }
    }
}

@Composable
fun SelectedSubjectDropdown(
    subjectItems: List<SelectedSubjects>,
    topRounded: Int = 100,
    bottomRounded: Int = 100,
    onSubjectSelected: (SelectedSubjects) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedSubject by remember { mutableStateOf(subjectItems.firstOrNull() ?: SelectedSubjects(0, "noChoice", R.drawable.function, 0xff000000, 0xff000000)) }

    Column {
        SelectSubject(
            fontColor = selectedSubject.color,
            backgroundColor = selectedSubject.backgroundColor,
            imageId = selectedSubject.imageId,
            topRounded = topRounded,
            bottomRounded = if (isExpanded) 0 else bottomRounded,
            isExpanded = isExpanded,
            onClick = { isExpanded = !isExpanded }
        )

        if (isExpanded) {
            subjectItems.forEachIndexed { index, subject ->
                SelectSubject(
                    fontColor = subject.color,
                    backgroundColor = subject.backgroundColor,
                    imageId = subject.imageId,
                    topRounded = if (index == 0) topRounded else 0,
                    bottomRounded = if (index == subjectItems.lastIndex) bottomRounded else 0,
                    onClick = {
                        selectedSubject = subject
                        onSubjectSelected(subject)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SelectSubjectPreview() {
    SelectSubject(backgroundColor = 0xff394557, fontColor = 0xff8AB4F8, imageId = R.drawable.function)
}

@Preview
@Composable
private fun StartStudyScreenPreview() {
        val mockRepository = object : AppRepository {
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

        override suspend fun getSelectedSubjects(): Flow<List<SelectedSubjects>> {
            TODO("Not yet implemented")
        }
        }

        val viewModel = StartStudyViewModel(mockRepository)
        StartStudyScreen(viewModel = viewModel)
}