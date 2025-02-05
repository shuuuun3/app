package com.example.studyapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.studyapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [VocabularyEntity::class, QuestionEntity::class, PairAnswerEntity::class, CompletionAnswerEntity::class, ChoiceAnswerEntity::class, SelectedSubjects::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val appDao: AppDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { Instance = it }
            }
        }
    }

    private class PrepopulateCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                Instance?.appDao?.insertAllSelectedSubjects(
                    listOf(
                        SelectedSubjects(1, "math", R.drawable.function, 0xff8AB4F8, 0xff394557),
                        SelectedSubjects(2, "english", R.drawable.earth, 0xff9C92F7, 0xff313142)
                    )
                )
            }
        }
    }

}
