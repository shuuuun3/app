package com.example.studyapp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter

@Entity(tableName = "vocabularies")
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = true) val vocabularyId: Int = 0,
    val title: String,
    val iconColor: Long,
    val description: String
)

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val questionId: Int = 0,
    val vocabularyId: Int,
    val questionType: String,
    val questionText: String,
    val isLiked: Boolean = false,
    val correctedNumber: Int = 0,
    val uncorrectedNumber: Int = 0
) {
    fun getMemorability(): String {
        val totalAttempts = correctedNumber + uncorrectedNumber

        return when {
            totalAttempts == 0 -> "unanswered"
            correctedNumber >= 5 && uncorrectedNumber == 0 -> "perfect"
            correctedNumber >= 3 && uncorrectedNumber <= 2 -> "learned"
            correctedNumber in 1..2 && uncorrectedNumber in 1..3 -> "vague"
            correctedNumber == 0 && uncorrectedNumber > 0 -> "weak"
            else -> "weak"  // デフォルトはWEAK
        }
    }
}

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
    val completionAnswers: CompletionAnswerEntity?,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val choiceAnswer: ChoiceAnswerEntity?
)

@Entity(tableName = "subjects")
data class Subjects(
    @PrimaryKey(autoGenerate = true) val subjectId: Int = 0,
    val name:String,
    val imageId: Int,
    val color: Long,
    val backgroundColor: Long
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