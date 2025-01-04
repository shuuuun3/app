package com.example.studyapp.ui.functions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.ui.TopAppBarDefaults
import com.example.studyapp.ui.navigation.NavigationDestination

object WordBookDestinations : NavigationDestination {
    override val route = "wordbook"
    override val titleRes = R.string.entry_wordbook
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordBookScreen(
    modifier: Modifier = Modifier
) {
    Scaffold (
        topBar = {
            TopAppBarDefaults(
                title = stringResource(R.string.wordbook_home),
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            Text(text = "WordBook")
        }
    }
}

@Preview
@Composable
private fun WordBookScreenPreview() {
    WordBookScreen(modifier = Modifier.padding(top = 0.dp))
}