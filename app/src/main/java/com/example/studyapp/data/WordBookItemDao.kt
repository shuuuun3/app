package com.example.studyapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface WordBookDao {
    // 単語帳全体を取得
    @Query("SELECT * FROM vocabularies")
    suspend fun getAllVocabularies(): List<VocabularyEntity>

    //質問と答えを取得
    @Transaction
    @Query("SELECT * FROM questions WHERE vocabularyId = :vocabularyId")
    suspend fun getQuestionWithAnswers(vocabularyId: Int): List<QuestionWithAnswers>

    // 単語帳を追加
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabulary(vocabulary: VocabularyEntity)

    // 単語帳を削除
    @Delete
    suspend fun deleteVocabulary(vocabulary: VocabularyEntity)

    @Update
    suspend fun updateVocabulary(vocabulary: VocabularyEntity)

    @Insert
    suspend fun insertQuestion(question: QuestionEntity): Long

    @Insert
    suspend fun insertQuestionAndGetId(question: QuestionEntity): Long {
        return insertQuestion(question)
    }

    @Update
    suspend fun updateQuestion(question: QuestionEntity)

    @Insert
    suspend fun insertPairAnswer(answer: PairAnswerEntity)

    @Update
    suspend fun updatePairAnswer(answer: PairAnswerEntity)

    @Insert
    suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity)

    @Update
    suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity)

    @Insert
    suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity)

    @Update
    suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity)
}
