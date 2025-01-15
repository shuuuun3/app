package com.example.studyapp.ui.functions.addstudy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.ui.navigation.NavigationDestination

object AddStudyDestinations : NavigationDestination {
    override val route = "addStudy"
    override val titleRes = R.string.entry_addStudy
}

@Composable
fun AddStudyScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "AddStudy")
}

@Preview
@Composable
private fun AddStudyScreenPreview() {
    AddStudyScreen(modifier = Modifier.padding(top = 0.dp))
}