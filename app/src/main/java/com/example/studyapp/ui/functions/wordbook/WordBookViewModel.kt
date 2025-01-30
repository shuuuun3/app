package com.example.studyapp.ui.functions.wordbook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.data.ChoiceAnswerEntity
import com.example.studyapp.data.CompletionAnswerEntity
import com.example.studyapp.data.PairAnswerEntity
import com.example.studyapp.data.QuestionEntity
import com.example.studyapp.data.QuestionWithAnswers
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.data.WordBookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WordBookUiState(
    var vocabularyList: StateFlow<List<VocabularyEntity>> = MutableStateFlow(emptyList()),
    var questionWithAnswersList: StateFlow<List<QuestionWithAnswers>> = MutableStateFlow(emptyList())
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

    fun loadQuestions(vocabularyId: Int) {
        viewModelScope.launch {
            wordBookRepository.getQuestionWithAnswers(vocabularyId).collect { itemList ->
                _wordBookUiState.value = _wordBookUiState.value.copy(
                    questionWithAnswersList = MutableStateFlow(itemList)
                )
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

    fun addQuestion(
        vocabularyId: Int,
        questionText: String,
        questionType: String,
        answerText: String,
        answerCompletion: List<String>? = null,
        answerChoices: List<String>? = null
    ) {
        viewModelScope.launch {
            val question = QuestionEntity(
                vocabularyId = vocabularyId,
                questionText = questionText,
                questionType = questionType
            )

            val questionId = wordBookRepository.insertQuestion(question)

            when (questionType) {
                "pair" -> {
                    val pairAnswer = PairAnswerEntity(
                        questionId = questionId,
                        answerText = answerText
                    )
                    wordBookRepository.insertPairAnswer(pairAnswer)
                }
                "completion" -> {
                    val completionAnswer = CompletionAnswerEntity(
                        questionId = questionId,
                        answerText = answerCompletion ?: emptyList()
                    )
                    wordBookRepository.insertCompletionAnswer(completionAnswer)
                }
                "choice" -> {
                    val choiceAnswer = ChoiceAnswerEntity(
                        questionId = questionId,
                        correctAnswer = answerText,
                        choices = answerChoices ?: emptyList()
                    )
                    wordBookRepository.insertChoiceAnswer(choiceAnswer)
                }
            }
            loadQuestions(vocabularyId)
            Log.d("WordBookViewModel", "Question added: $question")
        }
    }
}