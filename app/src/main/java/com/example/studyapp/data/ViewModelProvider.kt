package com.example.studyapp.data

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

object StartStudyViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            StartStudyViewModel(StudyAppApplication().appContainer.wordBookContainer.wordBookRepository)
        }
    }
}

fun CreationExtras.StudyAppApplication(): StudyAppApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
            as StudyAppApplication)