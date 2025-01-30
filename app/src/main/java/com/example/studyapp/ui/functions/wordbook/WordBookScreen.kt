package com.example.studyapp.ui.functions.wordbook

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyapp.R
import com.example.studyapp.data.ChoiceAnswerEntity
import com.example.studyapp.data.CompletionAnswerEntity
import com.example.studyapp.data.PairAnswerEntity
import com.example.studyapp.data.QuestionEntity
import com.example.studyapp.data.QuestionWithAnswers
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.data.WordBookRepository
import com.example.studyapp.ui.functions.wordbook.parts.TopAppBarDefaults
import com.example.studyapp.ui.functions.wordbook.parts.WordBookVocabularyInput
import com.example.studyapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object WordBookDestinations : NavigationDestination {
    override val route = "wordbook"
    override val titleRes = R.string.entry_wordbook
}

@Composable
fun WordBookScreen(
    viewModel: WordBookViewModel = viewModel(factory = WordBookViewModelProvider.Factory),
    navigateToVocabulary: (Int, String) -> Unit
) {
    val wordBookUiState by viewModel.wordBookUiState.collectAsState(initial = WordBookUiState())
    val vocabularyItems = wordBookUiState.vocabularyList.collectAsState(initial = listOf()).value
    var isReversed by remember { mutableStateOf(false) }

    WordBookScreenContent(
        vocabularyItems = vocabularyItems,
        onAddClick = { isReversed = !isReversed },
        onDeleteVocabulary = { vocabulary ->
            viewModel.deleteVocabulary(vocabulary)
        },
        navigateToVocabulary = navigateToVocabulary
    )

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
            WordBookVocabularyInput(
                onClick = { isReversed = false }
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordBookScreenContent(
    vocabularyItems: List<VocabularyEntity>,
    onAddClick: () -> Unit,
    onDeleteVocabulary: (VocabularyEntity) -> Unit = {},
    navigateToVocabulary: (Int, String) -> Unit
) {
    Box(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Column {
            TopAppBarDefaults()
            WordBookBody(
                vocabularyItems = vocabularyItems,
                onAddClick = onAddClick,
                onDelete = onDeleteVocabulary,
                navigateToVocabulary = navigateToVocabulary
            )
        }
    }
}

/*@Preview
@Composable
private fun WordBookScreenProPreview() {
    val mockObject = object : WordBookRepository {
        override fun getAllVocabularies(): Flow<List<VocabularyEntity>> = MutableStateFlow(
            listOf(
                VocabularyEntity(vocabularyId = 1, title = "test", iconColor = 0xFF0D99FF, description = "test"),
                VocabularyEntity(vocabularyId = 2, title = "test2", iconColor = 0xFF0D99FF, description = "test2"),
            )
        )

        override fun getQuestionWithAnswers(vocabularyId: Int): Flow<List<QuestionWithAnswers>> {
            TODO("Not yet implemented")
        }
        override suspend fun insertVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun deleteVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun updateVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun insertQuestion(question: QuestionEntity) {}
        override suspend fun updateQuestion(question: QuestionEntity) {}
        override suspend fun insertPairAnswer(answer: PairAnswerEntity) {}
        override suspend fun updatePairAnswer(answer: PairAnswerEntity) {}
        override suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity) {}
        override suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity) {}
        override suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity) {}
        override suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity) {}
    }
    WordBookScreen(
        viewModel = WordBookViewModel(mockObject),
        navigateToVocabulary = { _, _ -> }
    )
}*/
