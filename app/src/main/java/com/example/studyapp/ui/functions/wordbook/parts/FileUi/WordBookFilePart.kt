package com.example.studyapp.ui.functions.wordbook.parts.FileUi

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.functions.wordbook.parts.WordBookSmallButton
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookFilePart(
    vocabularyItems: List<VocabularyEntity> = listOf(),
    onAddClick: () -> Unit = {},
    onDelete: (VocabularyEntity) -> Unit = {},
    navigateToVocabulary: (Int, String) -> Unit
) {
    StudyAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .border(width = 1.dp, color = Color(0xff383838), shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp, end = 18.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "wordbook",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    WordBookSmallButton(
                        text = "select",
                        imageId = R.drawable.select,
                        width = 90,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    WordBookSmallButton(
                        text = "add",
                        imageId = R.drawable.add,
                        onClick = onAddClick
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(vocabularyItems) { item ->
                        WordBookFileItem(
                            title = item.title,
                            vocabulary = item,
                            iconColor = Color(item.iconColor),
                            onDelete = onDelete,
                            navigateToVocabulary = navigateToVocabulary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun WordBookFilePartPreview() {
    WordBookFilePart(
        navigateToVocabulary = { _, _ -> }
    )
}
