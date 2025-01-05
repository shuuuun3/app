package com.example.studyapp.ui.functions

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.ui.navigation.NavigationDestination

object SettingsDestinations : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.entry_settings
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "Settings")
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(modifier = Modifier.padding(top = 0.dp))
}