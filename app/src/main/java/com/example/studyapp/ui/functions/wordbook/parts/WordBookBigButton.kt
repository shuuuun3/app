package com.example.studyapp.ui.functions.wordbook.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookBigButton(
    onClick: () -> Unit = {},
    text: String = "",
    horizontalPadding: Dp = 40.dp,
    verticalPadding: Dp = 0.dp,
    width: Dp = 700.dp,
    backgroundColor: Color = Color(0xff6495ED),
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    StudyAppTheme {
        Box(
            modifier = Modifier
                .width(width)
                .height(50.dp)
                .padding(horizontalPadding, verticalPadding)
                .clip(RoundedCornerShape(20))
                .background(backgroundColor)
                .then(
                    if (borderWidth > 0.dp) Modifier.border(borderWidth, borderColor, RoundedCornerShape(20))
                    else Modifier
                )
                .clickable { onClick() }
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                color = textColor,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun WordBookBigButtonPreview() {
    WordBookBigButton()
}