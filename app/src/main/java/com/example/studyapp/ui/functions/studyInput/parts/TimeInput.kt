package com.example.studyapp.ui.functions.studyInput.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.xr.compose.subspace.SpatialBox
import com.example.studyapp.ui.theme.StudyAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeInput(
    inputtedTime: LocalDateTime
) {
    val formattedMonth = inputtedTime.format(DateTimeFormatter.ofPattern("M/d"))
    val formattedTime = inputtedTime.format(DateTimeFormatter.ofPattern("HH : mm"))
    StudyAppTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(90.dp)
                .width(160.dp)
                .clip(RoundedCornerShape(20))
                .background(Color(0xff202020))
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = formattedMonth,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formattedTime,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun TimeInputPreview() {
    TimeInput(inputtedTime = LocalDateTime.of(2025, 2, 21, 14, 30, 0))
}