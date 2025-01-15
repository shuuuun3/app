package com.example.studyapp.ui.functions.wordbook

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studyapp.StudyAppApplication

object WordBookViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            WordBookViewModel(StudyAppApplication().appContainer.wordBookContainer.wordBookRepository)
        }
    }
}

fun CreationExtras.StudyAppApplication(): StudyAppApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
            as StudyAppApplication)