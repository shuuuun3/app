package com.example.studyapp.ui.functions.startstudy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.Dp
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
import com.example.studyapp.data.Subjects
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun SelectSubject(
    subjectName: String,
    fontColor: Long,
    backgroundColor: Long,
    imageId: Int,
    onClick: () -> Unit = {},
    topRounded: Int = 100,
    bottomRounded: Int = 100,
    isExpanded: Boolean = false,
    height: Dp = 70.dp,
    isTop: Boolean = true
) {

    StudyAppTheme {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(364.dp)
                    .height(height)
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
                    text = subjectName,
                    fontSize = 18.sp,
                    color = Color(fontColor)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    if (isTop) {
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
                    }else {
                        Box(Modifier.size(20.dp))
                    }
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
    subjectItems: List<Subjects> = listOf(),
    topRounded: Int = 100,
    bottomRounded: Int = 100,
    onSubjectSelected: (Subjects) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedSubject by remember { mutableStateOf(subjectItems.first()) }

    var unselectedSubjects by remember { mutableStateOf(subjectItems.filterNot { it == selectedSubject }) }

    Column {
        SelectSubject(
            subjectName = selectedSubject.name,
            fontColor = selectedSubject.color,
            backgroundColor = selectedSubject.backgroundColor,
            imageId = selectedSubject.imageId,
            bottomRounded = if (isExpanded) 0 else 100,
            isExpanded = isExpanded,
            isTop = true,
            onClick = {
                isExpanded != isExpanded
            }
        )
        unselectedSubjects.forEachIndexed{ index, item ->
            if (index == unselectedSubjects.lastIndex) {
                SelectSubject(
                    subjectName = item.name,
                    fontColor = item.color,
                    backgroundColor = item.backgroundColor,
                    imageId = item.imageId,
                    topRounded = 0,
                    isTop = false,
                    height = if (isExpanded) 70.dp else 0.dp,
                    onClick = {
                        selectedSubject = item
                        onSubjectSelected(item)
                        isExpanded != isExpanded
                    }
                )
            }else {
                SelectSubject(
                    subjectName = item.name,
                    fontColor = item.color,
                    backgroundColor = item.backgroundColor,
                    imageId = item.imageId,
                    topRounded = 0,
                    bottomRounded = 0,
                    isTop = false,
                    height = if (isExpanded) 70.dp else 0.dp,
                    onClick = {
                        selectedSubject = item
                        onSubjectSelected(item)
                        isExpanded != isExpanded
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SelectSubjectPreview() {
    SelectSubject(backgroundColor = 0xff394557, fontColor = 0xff8AB4F8, imageId = R.drawable.function, subjectName = "math")
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

        override suspend fun getSelectedSubjects(): Flow<List<Subjects>> {
            TODO("Not yet implemented")
        }

        override suspend fun getSubjectById(subjectId: Int): Subjects {
            TODO("Not yet implemented")
        }
    }

    val viewModel = StartStudyViewModel(mockRepository)
    StartStudyScreen(viewModel = viewModel)
}