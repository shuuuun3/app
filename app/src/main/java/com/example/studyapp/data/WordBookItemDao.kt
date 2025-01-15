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

/*    // 単語帳とその質問を取得
    @Transaction
    @Query("SELECT * FROM vocabularies WHERE vocabularyId = :vocabularyId")
    suspend fun getVocabularyWithQuestions(vocabularyId: Int): VocabularyWithQuestions*/

    //質問と答えを取得
    @Transaction
    @Query("SELECT * FROM questions WHERE questionId = :id")
    suspend fun getQuestionWithAnswers(id: Int): QuestionWithAnswers

    // 単語帳を追加
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabulary(vocabulary: VocabularyEntity)

    // 単語帳を削除
    @Delete
    suspend fun deleteVocabulary(vocabulary: VocabularyEntity)

    @Update
    suspend fun updateVocabulary(vocabulary: VocabularyEntity)

    @Insert
    suspend fun insertQuestion(question: QuestionEntity)

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
