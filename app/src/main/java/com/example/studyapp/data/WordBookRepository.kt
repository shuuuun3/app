package com.example.studyapp.data

import kotlinx.coroutines.flow.Flow

interface WordBookRepository {
    fun getAllVocabularies(): Flow<List<VocabularyEntity>>
    fun getQuestionWithAnswers(vocabularyId: Int): Flow<List<QuestionWithAnswers>>
    suspend fun insertVocabulary(vocabulary: VocabularyEntity)
    suspend fun deleteVocabulary(vocabulary: VocabularyEntity)
    suspend fun updateVocabulary(vocabulary: VocabularyEntity)
    suspend fun insertQuestion(question: QuestionEntity)
    suspend fun insertQuestionAndGetId(question: QuestionEntity): Long
    suspend fun updateQuestion(question: QuestionEntity)
    suspend fun insertPairAnswer(answer: PairAnswerEntity)
    suspend fun updatePairAnswer(answer: PairAnswerEntity)
    suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity)
    suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity)
    suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity)
    suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity)
}