package com.example.studyapp.ui.functions.account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyapp.R
import com.example.studyapp.ui.navigation.NavigationDestination

object AccountDestinations : NavigationDestination {
    override val route = "account"
    override val titleRes = R.string.entry_account
}

@Composable
fun AccountScreen() {
    Text(text = "Account")
}

@Preview
@Composable
private fun AccountScreenPreview() {
    AccountScreen()
}