package com.example.studyapp.ui.functions.wordbook

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyapp.R
import com.example.studyapp.data.ChoiceAnswerEntity
import com.example.studyapp.data.CompletionAnswerEntity
import com.example.studyapp.data.PairAnswerEntity
import com.example.studyapp.data.QuestionEntity
import com.example.studyapp.data.QuestionWithAnswers
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.data.WordBookRepository
import com.example.studyapp.ui.functions.wordbook.parts.TopAppBarVocabulary
import com.example.studyapp.ui.functions.wordbook.parts.WordBookQuestionInput
import com.example.studyapp.ui.functions.wordbook.parts.WordBookQuestionItem
import com.example.studyapp.ui.functions.wordbook.parts.WordBookSmallButton
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun WordBookVocabulary(
    viewModel: WordBookViewModel = viewModel(factory = WordBookViewModelProvider.Factory),
    title: String,
    createdDate: String = "2025/1/1",
    vocabularyId: Int,
    navigateBack: () -> Unit = {},
) {
    LaunchedEffect(vocabularyId) {
        viewModel.loadQuestions(vocabularyId)
    }

    val wordBookUiState by viewModel.wordBookUiState.collectAsState()
    val questionWithAnswersItems = wordBookUiState.questionWithAnswersList.collectAsState(initial = listOf()).value

    var isReversed by remember { mutableStateOf(false) }

    Box {
        if(isReversed) {
            BackHandler {
                isReversed = false
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            isReversed = false
                        })
                    }
            ) {
                WordBookQuestionInput(
                    vocabularyId = vocabularyId,
                    onClick = { isReversed = false }
                )
            }
        }else {
            StudyAppTheme {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = 24.dp)
                ) {
                    item{
                        TopAppBarVocabulary(canNavigateBack = true, navigateBack = navigateBack)
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        )
                        Text(
                            text = createdDate,
                            fontSize = 10.sp,
                            color = Color(0xff7B7B7B),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        )
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xff383838),
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(text = "Memorability")
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xff383838),
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(0.dp, 16.dp)
                            ) {
                                Row {
                                    Text(
                                        text = "Word List",
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(20.dp, 0.dp)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    WordBookSmallButton(
                                        imageId = R.drawable.eye,
                                        text = "answer",
                                        width = 90
                                    )
                                    Spacer(modifier = Modifier.width(15.dp))
                                    WordBookSmallButton(
                                        imageId = R.drawable.add,
                                        text = "add",
                                        onClick = { isReversed = true }
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Column {
                                    if ( questionWithAnswersItems != null ) {
                                        questionWithAnswersItems.forEach { item ->
                                            val answers = when {
                                                item.pairAnswer != null -> item.pairAnswer.answerText
                                                item.completionAnswers != null -> item.completionAnswers.answerText.joinToString(",")
                                                item.choiceAnswer != null -> item.choiceAnswer.correctAnswer
                                                else -> "No answer"
                                            }
                                            WordBookQuestionItem(
                                                question = item.question.questionText,
                                                answer = answers
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                        }
                                    } else {
                                        Text(text = "no question")
                                    }
                                }
                            }
                        }
                    }

                    /*if (questionWithAnswersItems != null) {
                        items(questionWithAnswersItems) { item ->
                            val answers = when {
                                item.pairAnswer != null -> item.pairAnswer.answerText
                                item.completionAnswers != null -> item.completionAnswers.answerText.joinToString(",")
                                item.choiceAnswer != null -> item.choiceAnswer.correctAnswer
                                else -> "No answer"
                            }
                            WordBookQuestionItem(
                                question = item.question.questionText,
                                answer = answers
                            )
                        }
                    }else item {Text(text = "no question") }*/
                }
            }
        }
    }
}

/*@Preview
@Composable
private fun WordBookVocabularyPreview() {
    val mockRepository = object : WordBookRepository {
        override fun getQuestionWithAnswers(questionId: Int): Flow<List<QuestionWithAnswers>> {
            return MutableStateFlow(
                listOf(
                    QuestionWithAnswers(
                        question = QuestionEntity(
                            questionId = 1,
                            vocabularyId = 1,
                            questionType = "pair",
                            questionText = "What is the capital of France?"
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
                            questionText = "Complete the sentence: The sky is ___?"
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
                            questionText = "Choose the correct answer: 2+2 = ?"
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
        override suspend fun updateVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun insertQuestion(question: QuestionEntity) {}
        override suspend fun insertQuestionAndGetId(question: QuestionEntity): Int {
            TODO("Not yet implemented")
        }

        override suspend fun updateQuestion(question: QuestionEntity) {}
        override suspend fun insertPairAnswer(answer: PairAnswerEntity) {}
        override suspend fun updatePairAnswer(answer: PairAnswerEntity) {}
        override suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity) {}
        override suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity) {}
        override suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity) {}
        override suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity) {}
    }

    val viewModel = WordBookViewModel(mockRepository)

    WordBookVocabulary(viewModel = viewModel, title = "Vocabulary Example", createdDate = "2025/01/01", vocabularyId = 1)
}*/
