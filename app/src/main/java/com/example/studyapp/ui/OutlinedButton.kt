package com.example.studyapp.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@SuppressLint("ResourceAsColor")
@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonWidth: Int = 250,
    buttonHeight: Int = 68,
    textSize: Int = 32,
    color: Color = Color(0xFF63A3DF),
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(width = buttonWidth.dp, height = buttonHeight.dp)
            .background(Color.Transparent)
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(100)
            )
            .clip(shape = RoundedCornerShape(100))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = color,
            modifier = Modifier.background(Color.Transparent),
            fontSize = textSize.sp,
            fontWeight = FontWeight(300)
        )
    }
}

@Composable
fun LongPressButtonWithProgress(
    modifier: Modifier = Modifier,
    text: String,
    buttonWidth: Int = 250,
    buttonHeight: Int = 68,
    textSize: Int = 32,
    color: Color = Color(0xFF63A3DF),
    longPressDuration: Long = 2000L,  // 長押し時間 (ミリ秒)
    onClick: () -> Unit = {},
) {
    var isPressing by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }

    // アニメーションで進行状況を更新
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 100)
    )

    // 長押し検出
    LaunchedEffect(isPressing) {
        if (isPressing) {
            var elapsedTime = 0L
            while (elapsedTime < longPressDuration) {
                delay(10)  // 100msごとに進行状況を更新
                elapsedTime += 10
                progress = elapsedTime.toFloat() / longPressDuration.toFloat()
            }
            onClick() // 長押し完了後に onClick 呼び出し
        } else {
            progress = 0f // 長押しが解除されたときは進行状況をリセット
        }
    }

    Box(
        modifier = modifier
            .size(width = buttonWidth.dp, height = buttonHeight.dp)
            .background(Color.Transparent)
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(100))
            .clip(shape = RoundedCornerShape(100))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        isPressing = true
                        awaitRelease() // ここでボタンの長押しを検出
                        isPressing = false
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(width = (buttonWidth * animatedProgress).dp, height = buttonHeight.dp)
                    .background(color)
                    .clip(RoundedCornerShape(100))
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            text = text,
            color = color,
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