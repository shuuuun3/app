package com.example.studyapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [QuestionEntity::class, PairAnswerEntity::class, CompletionAnswerEntity::class, ChoiceAnswerEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WordBookDatabase : RoomDatabase() {
    abstract val wordBookDao: WordBookDao

    companion object {
        @Volatile
        private var Instance: WordBookDatabase? = null

        fun getDatabase(context: Context): WordBookDatabase {
            return Instance ?: synchronized(this) {
                return Instance ?: synchronized(this) {
                    Room.databaseBuilder(
                        context,
                        WordBookDatabase::class.java,
                        "wordbook_database"
                    ).build().also { Instance = it }
                }
            }
        }
    }

}