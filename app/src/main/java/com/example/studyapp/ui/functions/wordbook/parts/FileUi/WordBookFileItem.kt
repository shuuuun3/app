package com.example.studyapp.ui.functions.wordbook.parts.FileUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.studyapp.R
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookFileItem(
    title: String,
    value: Int = 0,
    vocabulary: VocabularyEntity,
    iconColor: Color = Color(0xFF0D99FF),
    onDelete: (VocabularyEntity) -> Unit = {},
) {
    StudyAppTheme {
        var isPopupVisible by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wordbook_file),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(iconColor),
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier
                ) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "volume $value",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        lineHeight = 15.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.topappbar_more),//moreの名前をを汎用高いものにする
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { isPopupVisible = true }
                )
                if (isPopupVisible) {
                    Popup(
                        alignment = Alignment.TopEnd,
                        onDismissRequest = { isPopupVisible = false },
                        properties = PopupProperties(focusable = true)
                    ) {
                        Card(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "move",
                                        fontSize = 15.sp,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clickable { isPopupVisible = false }
                                    )
                                    Text(
                                        text = "copy",
                                        fontSize = 15.sp,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clickable { isPopupVisible = false }
                                    )
                                    Text(
                                        text = "delete",
                                        fontSize = 15.sp,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clickable {
                                                isPopupVisible = false
                                                onDelete(vocabulary)
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(18.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WordBookFileItemPreview() {
    WordBookFileItem(title = "中間考査", value = 1, vocabulary = VocabularyEntity(vocabularyId = 1, title = "test", iconColor = 0xFF0D99FF, description = "test")) {}
}