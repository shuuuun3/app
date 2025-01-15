package com.example.studyapp.ui.functions.todo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyapp.R
import com.example.studyapp.ui.navigation.NavigationDestination

object TodoDestinations : NavigationDestination {
    override val route = "todo"
    override val titleRes = R.string.entry_todo
}

@Composable
fun TodoScreen() {
    Text(text = "Todo")
}

@Preview
@Composable
private fun TodoScreenPreview() {
    TodoScreen()
}