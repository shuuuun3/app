package com.example.studyapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R

@SuppressLint("ResourceAsColor")
@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonWidth: Int = 250,
    buttonHeight: Int = 68,
    textSize: Int = 32,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(width = buttonWidth.dp, height = buttonHeight.dp)
            .background(Color.Transparent)
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.button_blue),
                shape = RoundedCornerShape(100)
            )
            .clip(shape = RoundedCornerShape(100))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorResource(id = R.color.button_blue),
            modifier = Modifier.background(Color.Transparent),
            fontSize = textSize.sp,
            fontWeight = FontWeight(300)
        )
    }
}

@Preview
@Composable
private fun OutlinedButtonPreview() {
    OutlinedButton(text = "Quick Start")
}