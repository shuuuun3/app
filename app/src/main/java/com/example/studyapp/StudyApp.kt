package com.example.studyapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.ui.navigation.HomeNavHost

@Composable
fun StudyApp(
    navController: NavHostController = rememberNavController()
) {
    Box(modifier = Modifier.padding(top = 24.dp, bottom = 48.dp)) {
        HomeNavHost(navController = navController)
    }
}

@Preview
@Composable
private fun StudyAppPreview() {
    StudyApp()
}