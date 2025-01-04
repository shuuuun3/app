package com.example.studyapp.ui.functions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.studyapp.R
import com.example.studyapp.ui.navigation.NavigationDestination

object TodoDestinations : NavigationDestination {
    override val route = "todo"
    override val titleRes = R.string.entry_todo
}

@Composable
fun TodoScreen(
    modifier: Modifier = Modifier
) {

}