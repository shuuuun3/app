package com.example.studyapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseAppRepository(private val AppDao: AppDao) :
    AppRepository {
        override fun getAllVocabularies(): Flow<List<VocabularyEntity>> = flow { emit(AppDao.getAllVocabularies()) }
        override fun getQuestionWithAnswers(vocabularyId: Int): Flow<List<QuestionWithAnswers>> = flow { emit(AppDao.getQuestionWithAnswers(vocabularyId))  }
        override suspend fun insertVocabulary(vocabulary: VocabularyEntity) = AppDao.insertVocabulary(vocabulary)
        override suspend fun deleteVocabulary(vocabulary: VocabularyEntity) = AppDao.deleteVocabulary(vocabulary)
        override suspend fun deleteAllRelatedData(vocabularyId: Int) = AppDao.deleteAllRelatedData(vocabularyId)
        override suspend fun updateVocabulary(vocabulary: VocabularyEntity) = AppDao.updateVocabulary(vocabulary)
        override suspend fun insertQuestion(question: QuestionEntity): Int {
                val questionId: Long = AppDao.insertQuestion(question)
                return questionId.toInt()
        }
        override suspend fun updateQuestion(question: QuestionEntity) = AppDao.updateQuestion(question)
        override suspend fun updateIsLiked(questionId: Int, isLiked: Boolean) = AppDao.updateIsLiked(questionId, isLiked)
        override suspend fun insertPairAnswer(answer: PairAnswerEntity) = AppDao.insertPairAnswer(answer)
        override suspend fun updatePairAnswer(answer: PairAnswerEntity) = AppDao.updatePairAnswer(answer)
        override suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity) = AppDao.insertCompletionAnswer(answer)
        override suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity) = AppDao.updateCompletionAnswer(answer)
        override suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity) = AppDao.insertChoiceAnswer(answer)
        override suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity) = AppDao.updateChoiceAnswer(answer)
        override suspend fun updateUncorrectedNumber(questionId: Int, uncorrectedNumber: Int) = AppDao.updateUncorrectedNumber(questionId, uncorrectedNumber)
        override suspend fun updateCorrectedNumber(questionId: Int, correctNumber: Int) = AppDao.updateCorrectedNumber(questionId, correctNumber)
        override suspend fun getSelectedSubjects(): Flow<List<Subjects>> = flow { emit(AppDao.getSelectedSubjects()) }
        override suspend fun getSubjectById(subjectId: Int) = (AppDao.getSubjectById(subjectId))
        override suspend fun getStudyTimes(): Flow<List<StudyTimes>> = flow { emit(AppDao.getStudyTimes()) }
        override suspend fun insertStudyTime(studyTime: StudyTimes) = AppDao.insertStudyTime(studyTime)
        override suspend fun getAllStudyRecords(): Flow<List<StudyRecords>> = flow { emit(AppDao.getAllStudyRecords()) }
        override suspend fun getStudyRecordById(studyRecordId: Int): StudyRecords = AppDao.getStudyRecordById(studyRecordId)
        override suspend fun insertStudyRecord(studyRecord: StudyRecords): Int  {
            val studyRecordId: Long = AppDao.insertStudyRecord(studyRecord)
            return studyRecordId.toInt()
        }
        override suspend fun updateStudyRecord(studyRecord: StudyRecords) = AppDao.updateStudyRecord(studyRecord)
    }