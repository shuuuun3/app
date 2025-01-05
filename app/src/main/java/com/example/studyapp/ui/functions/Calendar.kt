package com.example.studyapp.ui.functions

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.ui.navigation.NavigationDestination

object CalendarDestinations : NavigationDestination {
    override val route = "calendar"
    override val titleRes = R.string.entry_calendar
}

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "Calendar")
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    CalendarScreen(modifier = Modifier.padding(top = 0.dp))
}