package com.example.studyapp.ui.functions.wordbook.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookQuestionItem(
    question:String,
    answer:String,
    progressIconColor: Color = Color(0xFFD9D9D9),
    questionNumber: Int = 1,
    onDelete: (VocabularyEntity) -> Unit = {},
) {
    StudyAppTheme {
        var isLiked by remember { mutableStateOf(true) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0xff383838), shape = RoundedCornerShape(10.dp))
                .clickable { }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$questionNumber",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 12.dp),
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier
                ) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = question,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(250.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = answer,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        lineHeight = 15.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(100))
                        .background(progressIconColor)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = if (isLiked) { painterResource(id = R.drawable.star_fill) }
                        else{ painterResource(id = R.drawable.star_outline)},
                    contentDescription = null,
                    colorFilter = if (isLiked) { ColorFilter.tint(Color(0xFF2F7CC5)) }
                        else{ ColorFilter.tint(Color(0xFF7B7B7B))},
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { isLiked = !isLiked }
                )
                Spacer(modifier = Modifier.width(18.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WordBookQuestionItemPreview() {
    WordBookQuestionItem(question = "あいうえおあああああああああああああああああああ", answer = "かきくけこ", questionNumber = 1)
}