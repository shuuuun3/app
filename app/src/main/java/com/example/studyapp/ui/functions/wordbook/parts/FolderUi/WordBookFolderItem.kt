package com.example.studyapp.ui.functions.wordbook.parts.FolderUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookFolderItem(
    title: String,
    value: Int,
    folderColor: Color = Color(0xFF0D99FF),
) {
    StudyAppTheme {
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
                    painter = painterResource(id = R.drawable.wordbook_folder),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(folderColor),
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
                        .clickable {  }
                )
                Spacer(modifier = Modifier.width(18.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WordBookItemFolderPreview() {
    WordBookFolderItem(title = "中間考査", value = 1)
}