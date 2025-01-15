package com.example.studyapp.ui.functions.wordbook

import androidx.lifecycle.ViewModel
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.data.WordBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class WordBookUiState(val itemList: Flow<List<VocabularyEntity>> = flowOf(listOf()))

class WordBookViewModel(wordBookRepository: WordBookRepository) : ViewModel() {
    val wordBookUiState = WordBookUiState(wordBookRepository.getAllVocabularies())
}