package com.example.studyapp.ui.functions.wordbook.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookSmallButton(
    imageId : Int,
    text: String,
    width: Int = 75,
    height: Int = 32,
    onClick: () -> Unit = {}
) {
    StudyAppTheme {
        Box(
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
                .clip(RoundedCornerShape(100))
                .background(Color(0xFF3C3C3C))
                .clickable { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(17.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    fontSize = 13.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun WordBookSmallButtonPreview() {
    WordBookSmallButton(text = "add", imageId = R.drawable.add)
}