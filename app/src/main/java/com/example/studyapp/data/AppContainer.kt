package com.example.studyapp.data

import android.content.Context

interface AppContainer {
    val wordBookContainer: WordBookContainer
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val wordBookContainer: WordBookContainer by lazy {
        WordBookAppContainer(applicationContext)
    }
}

interface WordBookContainer {
    val wordBookRepository: AppRepository
}

class WordBookAppContainer(private val context: Context) : WordBookContainer {
    override val wordBookRepository: AppRepository by lazy {
        DatabaseAppRepository(AppDatabase.getDatabase(context).appDao)
    }

}