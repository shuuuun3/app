package com.example.studyapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseWordbookRepository(private val wordBookDao: WordBookDao) :
        WordBookRepository {
            override fun getAllVocabularies(): Flow<List<VocabularyEntity>> = flow { emit(wordBookDao.getAllVocabularies()) }
            override fun getQuestionWithAnswers(vocabularyId: Int): Flow<List<QuestionWithAnswers>> = flow { emit(wordBookDao.getQuestionWithAnswers(vocabularyId))  }
            override suspend fun insertVocabulary(vocabulary: VocabularyEntity) = wordBookDao.insertVocabulary(vocabulary)
            override suspend fun deleteVocabulary(vocabulary: VocabularyEntity) = wordBookDao.deleteVocabulary(vocabulary)
            override suspend fun updateVocabulary(vocabulary: VocabularyEntity) = wordBookDao.updateVocabulary(vocabulary)
            override suspend fun insertQuestion(question: QuestionEntity) = wordBookDao.insertQuestion(question)
            override suspend fun insertQuestionAndGetId(question: QuestionEntity): Long = wordBookDao.insertQuestionAndGetId(question)
            override suspend fun updateQuestion(question: QuestionEntity) = wordBookDao.updateQuestion(question)
            override suspend fun insertPairAnswer(answer: PairAnswerEntity) = wordBookDao.insertPairAnswer(answer)
            override suspend fun updatePairAnswer(answer: PairAnswerEntity) = wordBookDao.updatePairAnswer(answer)
            override suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity) = wordBookDao.insertCompletionAnswer(answer)
            override suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity) = wordBookDao.updateCompletionAnswer(answer)
            override suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity) = wordBookDao.insertChoiceAnswer(answer)
            override suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity) = wordBookDao.updateChoiceAnswer(answer)
        }