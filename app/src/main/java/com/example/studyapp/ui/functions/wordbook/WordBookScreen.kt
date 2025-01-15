package com.example.studyapp.ui.functions.wordbook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.ui.functions.wordbook.parts.TopAppBarDefaults
import com.example.studyapp.ui.navigation.NavigationDestination

object WordBookDestinations : NavigationDestination {
    override val route = "wordbook"
    override val titleRes = R.string.entry_wordbook
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordBookScreen() {
    Box(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Column {
            TopAppBarDefaults()
            WordBookBody()
        }
    }
}

@Preview
@Composable
private fun WordBookScreenPreview() {
    WordBookScreen()
}