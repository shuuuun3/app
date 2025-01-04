package com.example.studyapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.ui.functions.TodoDestinations
import com.example.studyapp.ui.functions.WordBookDestinations
import com.example.studyapp.ui.home.HomeBody
import com.example.studyapp.ui.home.HomeDestinations
import com.example.studyapp.ui.navigation.HomeNavHost

@Composable
fun StudyApp(
    navController: NavHostController = rememberNavController()
) {
    Box(modifier = Modifier.padding(top = 24.dp)) {
        HomeNavHost(navController = navController)
    }
}

@Preview
@Composable
private fun StudyAppPreview() {
    StudyApp()
}