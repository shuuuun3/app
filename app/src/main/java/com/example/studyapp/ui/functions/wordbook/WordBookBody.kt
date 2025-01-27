package com.example.studyapp.ui.functions.wordbook

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.functions.wordbook.parts.FileUi.WordBookFilePart
import com.example.studyapp.ui.functions.wordbook.parts.FolderUi.WordBookFolderPart
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookBody(
    vocabularyItems: List<VocabularyEntity> = listOf(),
    onAddClick: () -> Unit = {},
    onDelete: (VocabularyEntity) -> Unit = {}
) {
    StudyAppTheme {
        Column {
            WordBookFolderPart()
            WordBookFilePart(
                vocabularyItems = vocabularyItems,
                onAddClick = onAddClick,
                onDelete = onDelete
            )
        }
    }
}

@Preview
@Composable
private fun WordBookBodyPreview() {
    WordBookBody()
}