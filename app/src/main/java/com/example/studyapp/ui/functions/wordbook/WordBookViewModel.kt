package com.example.studyapp.ui.functions.wordbook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.data.QuestionWithAnswers
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.data.WordBookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WordBookUiState(
    val vocabularyList: StateFlow<List<VocabularyEntity>> = MutableStateFlow(listOf()),
    val questionWithAnswersList: StateFlow<List<QuestionWithAnswers>> = MutableStateFlow(listOf())
)

class WordBookViewModel(private val wordBookRepository: WordBookRepository) : ViewModel() {
    private val _wordBookUiState = MutableStateFlow(WordBookUiState())
    val wordBookUiState: StateFlow<WordBookUiState> = _wordBookUiState

    init {
        loadVocabularies()
    }

    private fun loadVocabularies() {
        viewModelScope.launch {
            wordBookRepository.getAllVocabularies().collect { itemList ->
                _wordBookUiState.value = WordBookUiState(vocabularyList = MutableStateFlow(itemList))
            }
        }
    }

    private fun loadQuestions(vocabularyId: Int) {
        viewModelScope.launch {
            wordBookRepository.getQuestionWithAnswers(vocabularyId).collect { itemList ->
                _wordBookUiState.value = WordBookUiState(questionWithAnswersList = MutableStateFlow(itemList))
            }
        }
    }

    fun addVocabulary(
        title: String,
        description: String,
        iconColor: Long
    ) {
        val vocabulary = VocabularyEntity(
            title = title,
            description = description,
            iconColor = iconColor
        )
        viewModelScope.launch {
            wordBookRepository.insertVocabulary(vocabulary)
            Log.d("WordBookViewModel", "Vocabulary added: $vocabulary")
            loadVocabularies()
        }
    }

    fun deleteVocabulary(vocabulary: VocabularyEntity) {
        viewModelScope.launch {
            wordBookRepository.deleteVocabulary(vocabulary)
            Log.d("WordBookViewModel", "Vocabulary deleted: $vocabulary")
            loadVocabularies()
        }
    }
}