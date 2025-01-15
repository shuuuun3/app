package com.example.studyapp.ui.functions.wordbook

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyapp.ui.functions.wordbook.parts.WordBookFilePart
import com.example.studyapp.ui.functions.wordbook.parts.WordBookFolderPart
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookBody() {
    StudyAppTheme {
        Column {
            WordBookFolderPart()
            WordBookFilePart()
        }
    }
}

@Preview
@Composable
private fun WordBookBodyPreview() {
    WordBookBody()
}