package com.example.studyapp.ui.functions.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyapp.R
import com.example.studyapp.ui.AccountTopBar
import com.example.studyapp.ui.navigation.NavigationDestination

object CalendarDestinations : NavigationDestination {
    override val route = "calendar"
    override val titleRes = R.string.entry_calendar
}

@Composable
fun CalendarScreen(

) {
    Column {
        AccountTopBar(
            text = "Calendar",
            showHello = false,
            userName = null
        )
        CustomCalendar()
    }
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    CalendarScreen()
}