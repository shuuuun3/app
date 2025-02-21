package com.example.studyapp.ui.functions.wordbook.parts

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.studyapp.data.QuestionWithAnswers
import com.example.studyapp.data.WordBookViewModel
import com.example.studyapp.data.WordBookViewModelProvider
import com.example.studyapp.ui.OutlinedButton
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.delay

@Composable
fun WordBookAnswerScreen(
    viewModel: WordBookViewModel = viewModel(factory = WordBookViewModelProvider.Factory),
    vocabularyId: Int,
    vocabularyTitle: String = "Vocabulary Title",
    navigateToHome: () -> Unit = {}
) {
    LaunchedEffect(vocabularyId) {
        viewModel.loadQuestions(vocabularyId)
    }

    val wordBookUiState by viewModel.wordBookUiState.collectAsState()
    val questionWithAnswersItems = wordBookUiState.questionWithAnswersList.collectAsState(initial = listOf()).value


    val answerType by remember { mutableStateOf("selfGrading") }
    var questionNumber by remember { mutableIntStateOf(0) }
    var isAnswerShown by remember { mutableStateOf(false) }

    // questionNumberがlistのサイズより小さいかを確認してからアクセス
    val currentQuestion = if (questionWithAnswersItems.isNotEmpty() && questionNumber < questionWithAnswersItems.size) {
        questionWithAnswersItems[questionNumber]
    } else {
        null // 空のリストやインデックス範囲外の場合、nullを返す
    }

    currentQuestion?.let {
        WordBookAnswerContent(
            viewModel = viewModel,
            questionWithAnswersItems = it,
            vocabularyTitle = vocabularyTitle,
            answerType = answerType,
            questionNumber = questionNumber + 1,
            isAnswerShown = isAnswerShown,
            onCheckAnswer = { isAnswerShown = true },
            onNextQuestion = {
                questionNumber++
                isAnswerShown = false
            },
            allQuestionValue = questionWithAnswersItems.size
        )
    } ?: run {
        // 解答終了時の画面
        StudyAppTheme {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "Finish!",
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(60.dp))
                Text(text = "正答率や苦手問題の表示")
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(text = "Go to Home", onClick = navigateToHome)
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun WordBookAnswerContent(
    viewModel: WordBookViewModel = viewModel(factory = WordBookViewModelProvider.Factory),
    questionWithAnswersItems: QuestionWithAnswers,
    vocabularyTitle: String,
    answerType: String,
    questionNumber: Int,
    isAnswerShown: Boolean,
    onCheckAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    allQuestionValue: Int
) {
    StudyAppTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = vocabularyTitle,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "break",
                    fontSize = 15.sp,
                    color = Color(0xff6495ED),
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                        .clickable { }
                )
                Text(
                    text = "finish",
                    fontSize = 15.sp,
                    color = Color(0xff6495ED),
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                        .clickable { }
                )
                Image(
                    painterResource(id = R.drawable.settings),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { }
                )
            }

            Spacer(Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "No.${questionNumber}",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(5.dp)
                        .background(Color(0xff9E9E9E))
                ) {
                    Box(
                        modifier = Modifier
                            .width((200 * ((questionNumber - 1).toFloat() / allQuestionValue.toFloat())).dp)
                            .height(5.dp)
                            .background(Color(0xff36618E))
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = if (questionWithAnswersItems.question.isLiked) { painterResource(id = R.drawable.star_fill) }
                    else{ painterResource(id = R.drawable.star_outline)},
                    contentDescription = null,
                    colorFilter = if (questionWithAnswersItems.question.isLiked) { ColorFilter.tint(Color(0xFF2F7CC5)) }
                    else{ ColorFilter.tint(Color(0xFF7B7B7B))},
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            val newIsLiked = !questionWithAnswersItems.question.isLiked
                            viewModel.updateIsLiked(
                                questionWithAnswersItems.question.questionId,
                                newIsLiked
                            )
                        }
                )
            }

            Spacer(Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                MemorabilityIcon(memorability = questionWithAnswersItems.question.getMemorability())
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = questionWithAnswersItems.question.questionText,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )

            val answer = when {
                questionWithAnswersItems.pairAnswer != null -> questionWithAnswersItems.pairAnswer.answerText
                questionWithAnswersItems.completionAnswers != null -> questionWithAnswersItems.completionAnswers.answerText.joinToString(", ")
                else -> "No answer"
            }

            when (answerType) {
                "selfGrading" -> {
                    when (questionWithAnswersItems.question.questionType) {
                        "pair","completion" -> {
                            if (!isAnswerShown) {
                                Spacer(modifier = Modifier.weight(1f))
                                WordBookBigButton(
                                    text = "Check",
                                    onClick = onCheckAnswer,
                                    horizontalPadding = 0.dp
                                )
                            }else {
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = answer,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Row {
                                    WordBookBigButton(
                                        text = "uncorrected",
                                        width = 170.dp,
                                        backgroundColor = Color.Transparent,
                                        borderColor = Color(0xff6495ED),
                                        borderWidth = 1.dp,
                                        textColor = Color(0xff6495ED),
                                        horizontalPadding = 0.dp,
                                        onClick = {
                                            viewModel.updateUncorrectedNumber(
                                                questionWithAnswersItems.question.questionId,
                                                questionWithAnswersItems.question.uncorrectedNumber + 1
                                            )
                                            onNextQuestion()
                                        }
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    WordBookBigButton(
                                        text = "corrected",
                                        width = 170.dp,
                                        horizontalPadding = 0.dp,
                                        onClick = {
                                            viewModel.updateCorrectedNumber(
                                                questionWithAnswersItems.question.questionId,
                                                questionWithAnswersItems.question.correctedNumber + 1
                                            )
                                            onNextQuestion()
                                        }
                                    )
                                }
                            }
                        }
                        "choice" -> {
                            val correctAnswer = questionWithAnswersItems.choiceAnswer?.correctAnswer ?: ""
                            val choices = questionWithAnswersItems.choiceAnswer?.choices ?: listOf()

                            // 正解と選択肢を結合してシャッフル
                            val allChoices = (choices + correctAnswer).distinct().shuffled()

                            var selectedChoice by remember { mutableStateOf<String?>(null) }
                            var isAnswered by remember { mutableStateOf(false) }
                            var showNextQuestion by remember { mutableStateOf(false) } // 次の質問に進むフラグ

                            // 選択後に1秒遅延して次の質問へ
                            if (showNextQuestion) {
                                LaunchedEffect(Unit) {
                                    delay(1000)
                                    if (selectedChoice == correctAnswer) {
                                        viewModel.updateCorrectedNumber(
                                            questionWithAnswersItems.question.questionId,
                                            questionWithAnswersItems.question.correctedNumber + 1
                                        )
                                    } else {
                                        viewModel.updateUncorrectedNumber(
                                            questionWithAnswersItems.question.questionId,
                                            questionWithAnswersItems.question.uncorrectedNumber + 1
                                        )
                                    }
                                    onNextQuestion()
                                    isAnswered = false
                                    selectedChoice = null
                                    showNextQuestion = false
                                }
                            }

                            Column {
                                allChoices.forEach { choice ->
                                    Spacer(modifier = Modifier.height(20.dp))
                                    ChoiceItem(
                                        choice = choice,
                                        isSelected = selectedChoice == choice,
                                        isCorrect = if (isAnswered) choice == correctAnswer else null,
                                        onClick = {
                                            if (!isAnswered) {
                                                selectedChoice = choice
                                                isAnswered = true
                                                showNextQuestion = true  // 選択後にフラグを立てて遅延処理へ
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MemorabilityIcon(
    memorability: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(25.dp)
            .clip(RoundedCornerShape(100))
            .background(Color(0xff2A2A2A))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(RoundedCornerShape(100))
                    .background(
                        when (memorability) {
                            "perfect" -> Color(0xFF0058AB)
                            "learned" -> Color(0xFF2F7CC5)
                            "vague" -> Color(0xFF87B0D8)
                            "weak" -> Color(0xFFB8D2EA)
                            "unanswered" -> Color(0xFFD9D9D9)
                            else -> Color(0xFFD9D9D9)
                        }
                    )
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = memorability,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ChoiceItem(
    choice: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    isCorrect: Boolean?
) {
    val backgroundColor = when {
        isCorrect == true -> Color(0xFFA5D6A7)  // 緑 (正解)
        isCorrect == false -> Color(0xFFEF9A9A)  // 赤 (不正解)
        else -> Color(0xff6495ED)  // デフォルト
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(20))
            .background(backgroundColor)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = choice,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
private fun ChoiceItemPreview() {
    ChoiceItem(choice = "choice", onClick = {}, isSelected = false, isCorrect = null)
}


@Composable
fun PairAnswerSection(
    questionId: Int,
    answer: String,
    uncorrectedNumber: Int,
    correctedNumber: Int,
    onUpdateUncorrected: (Int) -> Unit,
    onUpdateCorrected: (Int) -> Unit,
    onNextQuestion: () -> Unit,
    type: String,
    choiceAnswer: List<String>? = null,
) {
    Column {
        when (type) {
            "pair","completion" -> {
                Text(
                    text = answer,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    WordBookBigButton(
                        text = "uncorrected",
                        width = 170.dp,
                        backgroundColor = Color.Transparent,
                        borderColor = Color(0xff6495ED),
                        borderWidth = 1.dp,
                        textColor = Color(0xff6495ED),
                        horizontalPadding = 0.dp,
                        onClick = {
                            onUpdateUncorrected(uncorrectedNumber + 1)
                            onNextQuestion()
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    WordBookBigButton(
                        text = "corrected",
                        width = 170.dp,
                        horizontalPadding = 0.dp,
                        onClick = {
                            onUpdateCorrected(correctedNumber + 1)
                            onNextQuestion()
                        }
                    )
                }
            }
            "choice" -> {
                
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPairAnswerSection() {
    PairAnswerSection(
        questionId = 1,
        answer = "Example Answer",
        uncorrectedNumber = 2,
        correctedNumber = 5,
        onUpdateUncorrected = { /* ダミー処理 */ },
        onUpdateCorrected = { /* ダミー処理 */ },
        onNextQuestion = { /* ダミー処理 */ },
        type = "choice",
        choiceAnswer = listOf("a", "i", "u", "e", "o")
    )
}