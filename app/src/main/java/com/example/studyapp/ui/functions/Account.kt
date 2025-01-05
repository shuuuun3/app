package com.example.studyapp.ui.functions

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.ui.navigation.NavigationDestination

object AccountDestinations : NavigationDestination {
    override val route = "account"
    override val titleRes = R.string.entry_account
}

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "Account")
}

@Preview
@Composable
private fun AccountScreenPreview() {
    AccountScreen(modifier = Modifier.padding(top = 0.dp))
}