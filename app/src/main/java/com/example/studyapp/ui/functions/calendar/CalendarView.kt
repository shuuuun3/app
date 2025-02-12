package com.example.studyapp.ui.functions.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CustomCalendar() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var currentMonth by remember { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // 月のナビゲーション
        MonthNavigation(currentMonth,
            onPreviousMonth = { currentMonth = currentMonth.minusMonths(1) },
            onNextMonth = { currentMonth = currentMonth.plusMonths(1) }
        )

        // 曜日ヘッダー
        WeekDaysHeader()

        Spacer(modifier = Modifier.height(24.dp))

        // カレンダー本体
        val days = getDaysForMonth(currentMonth)
        days.chunked(7).forEach { week ->
            Row(horizontalArrangement = Arrangement.Center) {
                week.forEach { date ->
                    DayView(date, isSelected = date == selectedDate) { selectedDate = date }
                }
            }
        }
    }
}

@Composable
fun MonthNavigation(currentMonth: LocalDate, onPreviousMonth: () -> Unit, onNextMonth: () -> Unit) {
    val monthName = currentMonth.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth, modifier = Modifier.size(32.dp)) {
            Image(painter = painterResource(id = R.drawable.chevron_left), contentDescription = null)
        }
        Text(text = "$monthName ${currentMonth.year}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xffEFEFEF))
        IconButton(onClick = onNextMonth, modifier = Modifier.size(32.dp)) {
            Image(painter = painterResource(id = R.drawable.chevron_right), contentDescription = null)
        }
    }
}

@Composable
fun WeekDaysHeader() {
    val daysOfWeek = listOf(DayOfWeek.SUNDAY) + DayOfWeek.entries.filter { it != DayOfWeek.SUNDAY }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 6.dp), horizontalArrangement = Arrangement.Center) {
        daysOfWeek.forEach { day ->
            Text(
                text = day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                color = Color(0xffEFEFEF),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DayView(date: LocalDate?, isSelected: Boolean, onClick: (LocalDate) -> Unit) {
    val today = LocalDate.now()

    val backgroundColor = when {
        date == null -> Color.Transparent
        date.dayOfWeek == DayOfWeek.SUNDAY -> Color(0xff634D4E)
        date.dayOfWeek == DayOfWeek.SATURDAY -> Color(0xff4D5863)
        else -> Color(0xff3D3D3D)
    }
    val textColor = Color(0xffEFEFEF)

    val modifier = if (date == today) {
        Modifier
            .padding(horizontal = 1.dp, vertical = 2.dp)
            .size(55.dp)
            .background(backgroundColor, shape = RoundedCornerShape(100))
            .clickable(enabled = date != null) { date?.let { onClick(it) } }
            .border(1.dp, Color(0xff868686), shape = RoundedCornerShape(100))
    }else {
        Modifier
            .padding(horizontal = 1.dp, vertical = 2.dp)
            .size(55.dp)
            .background(backgroundColor, shape = RoundedCornerShape(100))
            .clickable(enabled = date != null) { date?.let { onClick(it) } }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (date != null && isSelected) {
            Image(
                painter = painterResource(id = R.drawable.topappbar_more),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .offset(y = 15.dp)
                    .rotate(90f)
            )
        }
        date?.let {
            Text(
                text = it.dayOfMonth.toString(),
                fontSize = 15.sp,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun getDaysForMonth(month: LocalDate): List<LocalDate?> {
    val firstDayOfMonth = month.withDayOfMonth(1)
    val lastDayOfMonth = month.withDayOfMonth(month.lengthOfMonth())
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // 日曜: 0, 月曜: 1, ..., 土曜: 6

    val days = mutableListOf<LocalDate?>()

    // 前月の空白
    repeat(startDayOfWeek) { days.add(null) }

    // 当月の日付
    for (day in 1..lastDayOfMonth.dayOfMonth) {
        days.add(firstDayOfMonth.withDayOfMonth(day))
    }

    // 次月の空白（全体が7の倍数になるように調整）
    while (days.size % 7 != 0) {
        days.add(null)
    }

    return days
}

@Composable
fun WeeklyCalendar() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val today = LocalDate.now()

    // 今日の曜日に応じて直前の日曜日を取得
    val startOfWeek = today.minusDays(today.dayOfWeek.value % 7L) // 直前の日曜日（今日が日曜日の場合も含む）
    val daysOfWeek = (0..6).map { startOfWeek.plusDays(it.toLong()) } // 7日間取得

    Column(modifier = Modifier) {
        // 曜日ヘッダー（日曜日始まり）
        WeekDaysHeader()

        Spacer(modifier = Modifier.height(16.dp))

        // 今週の日付を横一列で表示
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            daysOfWeek.forEach { date ->
                DayView(date, isSelected = date == selectedDate) { selectedDate = date }
            }
        }
    }
}

@Preview
@Composable
private fun WeeklyCalendarPreview() {
    WeeklyCalendar()
}


@Preview
@Composable
private fun CalendarPreview() {
    CustomCalendar()
}

@Preview
@Composable
private fun DayViewPreview() {
    DayView(date = (LocalDate.now()), isSelected = false, onClick = {})
}