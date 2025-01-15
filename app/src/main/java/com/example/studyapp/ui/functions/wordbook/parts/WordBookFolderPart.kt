package com.example.studyapp.ui.functions.wordbook.parts

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookFolderPart() {
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
                        text = "folder",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    WordBookSmallButton(
                        text = "add",
                        imageId = R.drawable.add
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    WordBookFolderItem(title = "中間考査", value = 1)
                    WordBookFolderItem(title = "期末考査", value = 1, folderColor = Color(0xffFF8E0D))
                }
            }
        }
    }
}

@Preview
@Composable
private fun WordBookFolderPartPreview() {
    WordBookFolderPart()
}