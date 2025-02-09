package com.example.studyapp.data

import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAllVocabularies(): Flow<List<VocabularyEntity>>
    fun getQuestionWithAnswers(vocabularyId: Int): Flow<List<QuestionWithAnswers>>
    suspend fun insertVocabulary(vocabulary: VocabularyEntity)
    suspend fun deleteVocabulary(vocabulary: VocabularyEntity)
    suspend fun deleteAllRelatedData(vocabularyId: Int)
    suspend fun updateVocabulary(vocabulary: VocabularyEntity)
    suspend fun insertQuestion(question: QuestionEntity): Int
    suspend fun updateQuestion(question: QuestionEntity)
    suspend fun updateIsLiked(questionId: Int, isLiked: Boolean)
    suspend fun insertPairAnswer(answer: PairAnswerEntity)
    suspend fun updatePairAnswer(answer: PairAnswerEntity)
    suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity)
    suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity)
    suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity)
    suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity)
    suspend fun updateUncorrectedNumber(questionId: Int, uncorrectedNumber: Int)
    suspend fun updateCorrectedNumber(questionId: Int, correctNumber: Int)
    suspend fun getSelectedSubjects(): Flow<List<Subjects>>
    suspend fun getSubjectById(subjectId: Int): Subjects
    suspend fun getStudyTimes(): Flow<List<StudyTimes>>
    suspend fun insertStudyTime(studyTime: StudyTimes)
    suspend fun getAllStudyRecords(): Flow<List<StudyRecords>>
    suspend fun getStudyRecordById(studyRecordId: Int): StudyRecords
    suspend fun insertStudyRecord(studyRecord: StudyRecords): Int
    suspend fun updateStudyRecord(studyRecord: StudyRecords)
}