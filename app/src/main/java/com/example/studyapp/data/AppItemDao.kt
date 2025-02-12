package com.example.studyapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface AppDao {
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

    @Query("DELETE FROM vocabularies WHERE vocabularyId = :vocabularyId")
    suspend fun deleteVocabularyById(vocabularyId: Int)

    // vocabularyIdに関連するすべてのデータを一括削除
    @Transaction
    suspend fun deleteAllRelatedData(vocabularyId: Int) {
        // 最初に質問を削除
        val questions = getQuestionWithAnswers(vocabularyId)
        questions.forEach { question ->
            // 各回答を削除
            deleteAnswersByQuestionId(question.question.questionId)
        }

        deleteQuestionsByVocabularyId(vocabularyId)

        // 次にVocabularyを削除
        deleteVocabularyById(vocabularyId)
    }

    @Transaction
    suspend fun deleteQuestionAndAnswers(questionId: Int) {
        deleteAnswersByQuestionId(questionId)
        deleteQuestionByQuestionId(questionId)
    }

    @Transaction
    @Query("DELETE FROM questions WHERE vocabularyId = :vocabularyId")
    suspend fun deleteQuestionsByVocabularyId(vocabularyId: Int)

    @Query("DELETE FROM questions WHERE questionId = :questionId")
    suspend fun deleteQuestionByQuestionId(questionId: Int)

    @Transaction
    suspend fun deleteAnswersByQuestionId(questionId: Int) {
        // PairAnswersが存在する場合
        val pairAnswerExists = checkPairAnswerExists(questionId)
        if (pairAnswerExists) {
            deletePairAnswerByQuestionId(questionId)
        }

        // CompletionAnswersが存在する場合
        val completionAnswerExists = checkCompletionAnswerExists(questionId)
        if (completionAnswerExists) {
            deleteCompletionAnswerByQuestionId(questionId)
        }

        // ChoiceAnswersが存在する場合
        val choiceAnswerExists = checkChoiceAnswerExists(questionId)
        if (choiceAnswerExists) {
            deleteChoiceAnswerByQuestionId(questionId)
        }
    }

    @Query("SELECT EXISTS(SELECT 1 FROM pair_answers WHERE questionId = :questionId LIMIT 1)")
    suspend fun checkPairAnswerExists(questionId: Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM completion_answers WHERE questionId = :questionId LIMIT 1)")
    suspend fun checkCompletionAnswerExists(questionId: Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM choice_answers WHERE questionId = :questionId LIMIT 1)")
    suspend fun checkChoiceAnswerExists(questionId: Int): Boolean

    @Query("DELETE FROM pair_answers WHERE questionId = :questionId")
    suspend fun deletePairAnswerByQuestionId(questionId: Int)

    @Query("DELETE FROM completion_answers WHERE questionId = :questionId")
    suspend fun deleteCompletionAnswerByQuestionId(questionId: Int)

    @Query("DELETE FROM choice_answers WHERE questionId = :questionId")
    suspend fun deleteChoiceAnswerByQuestionId(questionId: Int)


    @Query(" DELETE FROM pair_answers WHERE questionId IN ( SELECT questionId FROM questions WHERE vocabularyId = :vocabularyId)")
    suspend fun deletePairAnswersByVocabularyId(vocabularyId: Int)

    @Query("DELETE FROM completion_answers WHERE questionId IN ( SELECT questionId FROM questions WHERE vocabularyId = :vocabularyId)")
    suspend fun deleteCompletionAnswersByVocabularyId(vocabularyId: Int)

    @Query("DELETE FROM choice_answers WHERE questionId IN (SELECT questionId FROM questions WHERE vocabularyId = :vocabularyId)")
    suspend fun deleteChoiceAnswersByVocabularyId(vocabularyId: Int)

    @Update
    suspend fun updateVocabulary(vocabulary: VocabularyEntity)

    @Insert
    suspend fun insertQuestion(question: QuestionEntity): Long
    @Update
    suspend fun updateQuestion(question: QuestionEntity)

    @Query("UPDATE questions SET isLiked = :isLiked WHERE questionId = :questionId")
    suspend fun updateIsLiked(questionId: Int, isLiked: Boolean)

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

    @Query("UPDATE questions SET uncorrectedNumber = :uncorrectedNumber WHERE questionId = :questionId")
    suspend fun updateUncorrectedNumber(questionId: Int, uncorrectedNumber: Int)

    @Query("UPDATE questions SET correctedNumber = :correctedNumber WHERE questionId = :questionId")
    suspend fun updateCorrectedNumber(questionId: Int, correctedNumber: Int)

    @Query("SELECT * FROM subjects")
    suspend fun getSelectedSubjects(): List<Subjects>

    @Query("SELECT * FROM subjects WHERE subjectId = :subjectId")
    suspend fun getSubjectById(subjectId: Int): Subjects

    @Insert
    suspend fun insertSelectedSubject(selectedSubject: Subjects)

    @Insert
    suspend fun insertAllSelectedSubjects(selectedSubjects: List<Subjects>)

    @Query("SELECT * FROM study_times")
    suspend fun getStudyTimes(): List<StudyTimes>

    @Insert
    suspend fun insertStudyTime(studyTime: StudyTimes)

    @Insert
    suspend fun insertAllStudyTimes(studyTimes: List<StudyTimes>)

    @Query("SELECT * FROM study_records")
    suspend fun getAllStudyRecords(): List<StudyRecords>

    @Query("SELECT * FROM study_records WHERE studyRecordId = :studyRecordId")
    suspend fun getStudyRecordById(studyRecordId: Int): StudyRecords

    @Insert
    suspend fun insertStudyRecord(studyRecord: StudyRecords): Long

    @Update
    suspend fun updateStudyRecord(studyRecord: StudyRecords)
}