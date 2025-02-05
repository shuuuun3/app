package com.example.studyapp

import android.app.Application
import com.example.studyapp.data.AppContainer
import com.example.studyapp.data.AppContainerImpl

class StudyAppApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(this)
    }
}
