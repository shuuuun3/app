package com.example.studyapp.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class WordBookUiState(
    var vocabularyList: StateFlow<List<VocabularyEntity>> = MutableStateFlow(emptyList()),
    var questionWithAnswersList: StateFlow<List<QuestionWithAnswers>> = MutableStateFlow(emptyList()),
)

data class StartStudyUiState(
    val isLoading: Boolean = true,
    var subjectList: StateFlow<List<Subjects>> = MutableStateFlow(emptyList()),
    var studyTimesList: StateFlow<List<StudyTimes>> = MutableStateFlow(emptyList()),
    var studyRecordsList: StateFlow<List<StudyRecords>> = MutableStateFlow(emptyList()),
)

class StartStudyViewModel(private val appRepository: AppRepository) : ViewModel() {
    private val _startStudyUiState = MutableStateFlow(StartStudyUiState())
    val startStudyUiState: StateFlow<StartStudyUiState> = _startStudyUiState

    init {
        loadSubjects()
        loadStudyTimes()
        loadStudyRecords()
    }

    private fun loadSubjects() {
        viewModelScope.launch {
            appRepository.getSelectedSubjects().collect { itemList ->
                Log.d("StartStudyViewModel", "Subjects Loaded: $itemList")
                _startStudyUiState.update { currentState ->
                    currentState.copy(
                        subjectList = MutableStateFlow(itemList),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadStudyTimes() {
        viewModelScope.launch {
            appRepository.getStudyTimes().collect { itemList ->
                _startStudyUiState.update { currentState ->
                    currentState.copy(
                        studyTimesList = MutableStateFlow(itemList),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadStudyRecords() {
        viewModelScope.launch {
            appRepository.getAllStudyRecords().collect { itemList ->
                _startStudyUiState.update { currentState ->
                    currentState.copy(
                        studyRecordsList = MutableStateFlow(itemList)
                    )
                }
            }
        }
    }

    suspend fun getSubjectsById(subjectId: Int): Subjects {
        return appRepository.getSubjectById(subjectId)
    }

    fun addStudyTimes(
        studyTime: Int
    ) {
        val studyTimes = StudyTimes(
            studyTime = studyTime
        )
        viewModelScope.launch {
            appRepository.insertStudyTime(studyTimes)
            loadStudyTimes()
        }
    }

    private suspend fun getStudyRecordById(studyRecordId: Int): StudyRecords {
        return appRepository.getStudyRecordById(studyRecordId)
    }

    suspend fun addStudyRecord(
        subjectId: Int?,
        title: String?,
        description: String?,
        studiedTime: Int?,
        studyDate: LocalDate
    ): Int {
        val studyRecord = StudyRecords(
            studyRecordId = 0,
            subjectId = subjectId,
            title = title,
            description = description,
            studiedTime = studiedTime,
            studyDate = studyDate
        )

        val studyRecordId = appRepository.insertStudyRecord(studyRecord)
        Log.d("StudyViewModel", "Study record added: $studyRecord, id: $studyRecordId")

        return studyRecordId
    }

    suspend fun updateStudyRecord(
        studyRecordId: Int,
        subjectId: Int? = null,
        title: String? = null,
        description: String? = null,
        studiedTime: Int? = null
    ) {
        // 現在のStudyRecordを取得する（例: IDで検索）
        val studyRecord = getStudyRecordById(studyRecordId) // ここは適宜実装

        // 更新したい部分だけ変更
        val updatedStudyRecord = studyRecord.copy(
            subjectId = subjectId ?: studyRecord.subjectId,
            title = title ?: studyRecord.title,
            description = description ?: studyRecord.description,
            studiedTime = studiedTime ?: studyRecord.studiedTime,
            studyDate = studyRecord.studyDate
        )

        appRepository.updateStudyRecord(updatedStudyRecord)
        loadStudyRecords()
    }
}

class WordBookViewModel(private val appRepository: AppRepository) : ViewModel() {
    private val _wordBookUiState = MutableStateFlow(WordBookUiState())
    val wordBookUiState: StateFlow<WordBookUiState> = _wordBookUiState

    init {
        loadVocabularies()
    }

    private fun loadVocabularies() {
        viewModelScope.launch {
            appRepository.getAllVocabularies().collect { itemList ->
                _wordBookUiState.value = WordBookUiState(vocabularyList = MutableStateFlow(itemList))
            }
        }
    }

    fun loadQuestions(vocabularyId: Int) {
        viewModelScope.launch {
            appRepository.getQuestionWithAnswers(vocabularyId).collect { itemList ->
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
            appRepository.insertVocabulary(vocabulary)
            Log.d("WordBookViewModel", "Vocabulary added: $vocabulary")
            loadVocabularies()
        }
    }

    fun deleteVocabulary(vocabulary: VocabularyEntity) {
        viewModelScope.launch {
            appRepository.deleteVocabulary(vocabulary)
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

            val questionId = appRepository.insertQuestion(question)

            when (questionType) {
                "pair" -> {
                    val pairAnswer = PairAnswerEntity(
                        questionId = questionId,
                        answerText = answerText
                    )
                    appRepository.insertPairAnswer(pairAnswer)
                }
                "completion" -> {
                    val completionAnswer = CompletionAnswerEntity(
                        questionId = questionId,
                        answerText = answerCompletion ?: emptyList()
                    )
                    appRepository.insertCompletionAnswer(completionAnswer)
                }
                "choice" -> {
                    val choiceAnswer = ChoiceAnswerEntity(
                        questionId = questionId,
                        correctAnswer = answerText,
                        choices = answerChoices ?: emptyList()
                    )
                    appRepository.insertChoiceAnswer(choiceAnswer)
                }
            }
            loadQuestions(vocabularyId)
            Log.d("WordBookViewModel", "Question added: $question")
        }
    }

    fun updateIsLiked(questionId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            appRepository.updateIsLiked(questionId, isLiked)
            loadQuestions(wordBookUiState.value.questionWithAnswersList.value.first().question.vocabularyId)
        }
    }

    fun updateUncorrectedNumber(questionId: Int, uncorrectedNumber: Int) {
        viewModelScope.launch {
            appRepository.updateUncorrectedNumber(questionId, uncorrectedNumber)
            loadQuestions(wordBookUiState.value.questionWithAnswersList.value.first().question.vocabularyId)
        }
    }

    fun updateCorrectedNumber(questionId: Int, correctedNumber: Int) {
        viewModelScope.launch {
            appRepository.updateCorrectedNumber(questionId, correctedNumber)
            loadQuestions(wordBookUiState.value.questionWithAnswersList.value.first().question.vocabularyId)
        }
    }

    fun deleteAllRelatedData(vocabularyId: Int) {
        viewModelScope.launch {
            appRepository.deleteAllRelatedData(vocabularyId)
            Log.d("WordBookViewModel", "All related data deleted for vocabularyId: $vocabularyId")
            loadVocabularies()
        }
    }

}