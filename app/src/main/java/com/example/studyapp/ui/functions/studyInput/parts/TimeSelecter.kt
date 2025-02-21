package com.example.studyapp.ui.functions.studyInput.parts

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyapp.R
import com.example.studyapp.data.StartStudyViewModel
import com.example.studyapp.data.StartStudyViewModelProvider
import com.example.studyapp.data.StudyTimes
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlin.math.ln

@SuppressLint("SuspiciousIndentation")
@Composable
fun TimeSelector(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    studyTimesItems: List<StudyTimes> = listOf(),
    onTimeSelected: (Int) -> Unit = {}
) {
    var selectedId by remember { mutableStateOf<Int?>(null) }
    var isAddTimeClicked by remember { mutableStateOf(false) }

    if (isAddTimeClicked) {
        var inputTimeText by remember { mutableStateOf("") }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(90.dp)
                .width(364.dp)
                .clip(RoundedCornerShape(100))
                .background(Color(0xff202020))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputTimeText,
                    onValueChange = { newText ->
                        inputTimeText = newText
                    },
                    placeholder = {
                        Text(
                            text = "multiples of 10",
                            color = Color(0xff525252),
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(100))
                        .background(Color(0xff434343))
                        .clickable {
                            if (inputTimeText.isNotEmpty() && inputTimeText.toIntOrNull() != null) {
                                viewModel.addStudyTimes(
                                    studyTime = inputTimeText.toIntOrNull() ?: 0
                                )
                            }
                            isAddTimeClicked = false
                            inputTimeText = ""
                            selectedId = null
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                    )
                }
            }
        }
    }else
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(90.dp)
            .width(364.dp)
            .clip(RoundedCornerShape(100))
            .background(Color(0xff202020))
    ) {
        if (selectedId != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(2f)
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .pointerInput(Unit) {
                            detectTapGestures { selectedId = null }
                        }
                        .clip(RoundedCornerShape(100))
                        .background(Color(0xff202020))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .zIndex(1f)
                .height(90.dp)
                .width(364.dp)
                .clip(RoundedCornerShape(100))
                .background(Color(0xff202020))
        ) {
            item {
                val spacerTargetWidth = if (selectedId != null) 0.dp else 10.dp
                val animatedSpacerWidth by animateDpAsState(
                    targetValue = spacerTargetWidth,
                    animationSpec = tween(durationMillis = 500)
                )

                val contentTargetSize = if (selectedId != null) 0.dp else 70.dp
                val animatedContentSize by animateDpAsState(
                    targetValue = contentTargetSize,
                    animationSpec = tween(durationMillis = 500)
                )

                Spacer(modifier = Modifier.width(animatedSpacerWidth))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(animatedContentSize)
                        .clip(RoundedCornerShape(100))
                        .background(Color(0xff434343))
                        .clickable { isAddTimeClicked = true }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.time_add),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            items(studyTimesItems) { item ->
                val spacerTargetWidth = if (selectedId != null) {
                    if (item.studyTimeId == selectedId) 10.dp else 0.dp
                } else {
                    10.dp
                }
                val animatedSpacerWidth by animateDpAsState(
                    targetValue = spacerTargetWidth,
                    animationSpec = tween(durationMillis = 500)
                )
                if (selectedId != item.studyTimeId) Spacer(modifier = Modifier.width(animatedSpacerWidth))
                TimeContent(
                    timeInMinutes = item.studyTime,
                    // このアイテムが選択されているかどうかを渡す
                    isSelected = item.studyTimeId == selectedId,
                    // 何かが選択されているか（選択があれば未選択のアイテムは幅0になる）
                    hasSelection = (selectedId != null),
                    onClick = {
                        // 選択されたアイテムのIDを更新し、コールバックを呼び出す
                        selectedId = item.studyTimeId
                        onTimeSelected(item.studyTime)
                    }
                )
            }

            item {
                val spacerTargetWidth = if (selectedId != null) 0.dp else 10.dp
                val animatedSpacerWidth by animateDpAsState(
                    targetValue = spacerTargetWidth,
                    animationSpec = tween(durationMillis = 500)
                )
                Spacer(modifier = Modifier.width(animatedSpacerWidth))
            }
        }
    }
}

@Preview
@Composable
private fun TimeSelectorPreview() {
    TimeSelector(studyTimesItems = listOf(StudyTimes(1, 10), StudyTimes(2, 20), StudyTimes(3, 60)))
}

@Composable
fun TimeContent(
    timeInMinutes: Int = 130,
    isSelected: Boolean = false,
    hasSelection: Boolean = false,
    onClick: () -> Unit = {},
) {
    // timeInMinutesに基づいてデフォルトの幅を計算する
    val computedWidth = when {
        timeInMinutes < 60 -> {
            // 60分未満の場合： A + B * ln(timeInMinutes) で計算
            (24.15 + 21.64 * ln(timeInMinutes.toDouble())).dp
        }
        timeInMinutes < 120 -> {
            // 60分〜119分の場合：60分時の値から1分あたりextraPerMinute分ずつ増加
            val baseWidth = 24.15 + 21.64 * ln(60.0)  // 60分時で約112.8dp
            val extraPerMinute = 0.2  // 必要に応じて調整
            (baseWidth + extraPerMinute * (timeInMinutes - 60)).dp
        }
        else -> {
            // 120分以上の場合：120分時の幅に固定する
            val baseWidth = 24.15 + 21.64 * ln(60.0)
            val extraPerMinute = 0.2
            (baseWidth + extraPerMinute * 60).dp
        }
    }

    // hasSelectionがtrueの場合は、選択されているかどうかで幅を決定する
    // 選択されていれば親の幅364.dp、そうでなければ0.dpにする
    // 何も選択されていなければ、computedWidthを使用する
    val targetWidth = if (hasSelection) {
        if (isSelected) 364.dp else 0.dp
    } else {
        computedWidth
    }

    // 幅の変化をアニメーション（500msのtweenを使用）で実現
    val animatedWidth by animateDpAsState(
        targetValue = targetWidth,
        animationSpec = tween(durationMillis = 500)
    )


    // timeInMinutes は 10 単位の値しか入らない前提です。
    // 60 未満の場合は hour = 0, minute に timeInMinutes の値をそのまま代入
    // 60 以上の場合は、hour = timeInMinutes / 60, minute = timeInMinutes % 60 として変換
    val (hour, minute) = if (timeInMinutes < 60) {
        0 to timeInMinutes
    } else {
        timeInMinutes / 60 to timeInMinutes % 60
    }

    StudyAppTheme {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .width(animatedWidth)
                .height(70.dp)
                .clip(RoundedCornerShape(100))
                .background(Color(0xff5984D4))
                .clickable { onClick() }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = if (timeInMinutes < 60) "$timeInMinutes" else "$hour:$minute",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 25.sp
                )
            }
            Text(
                text = if (timeInMinutes < 60) "min" else "h",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 10.sp
            )
        }
    }
}

@Preview
@Composable
private fun TimeContentPreview() {
    TimeContent()
}