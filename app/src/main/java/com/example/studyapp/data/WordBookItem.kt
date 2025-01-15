package com.example.studyapp.data

import android.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter

@Entity(tableName = "vocabularies")
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = true)
    val vocabularyId: Int = 0,
    val title: String,
    val iconColor: Color,
    val description: String
)

/*data class VocabularyWithQuestions(
    @Embedded val vocabulary: VocabularyEntity,
    @Relation(
        parentColumn = "vocabularyId",
        entityColumn = "vocabularyId"
    )
    val questions: List<QuestionWithAnswers>
)*/

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val questionId: Int = 0,
    val vocabularyId: Int,
    val questionType: QuestionType,
    val questionText: String
)

@Entity(tableName = "pair_answers")
data class PairAnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val pairAnswerId: Int = 0,
    val questionId: Int,
    val answerText: String
)

@Entity(tableName = "completion_answers")
data class CompletionAnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val completionAnswerId: Int = 0,
    val questionId: Int,
    val answerText: List<String>
)

@Entity(tableName = "choice_answers")
data class ChoiceAnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val choiceAnswerId: Int = 0,
    val questionId: Int,
    val correctAnswer: String,
    val choices: List<String>
)

enum class QuestionType {
    PAIR,
    COMPLETION,
    CHOICE
}

data class QuestionWithAnswers(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val pairAnswer: PairAnswerEntity?,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val completionAnswers: List<CompletionAnswerEntity>?,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val choiceAnswer: ChoiceAnswerEntity?
)

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split(",")
    }
}