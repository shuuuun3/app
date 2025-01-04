package com.example.studyapp.ui.navigation

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studyapp.ui.TabBar
import com.example.studyapp.ui.functions.TodoDestinations
import com.example.studyapp.ui.functions.TodoScreen
import com.example.studyapp.ui.functions.WordBookDestinations
import com.example.studyapp.ui.functions.WordBookScreen
import com.example.studyapp.ui.home.HomeBody
import com.example.studyapp.ui.home.HomeDestinations


@Composable
fun HomeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box() {
        NavHost(
            navController = navController,
            startDestination = HomeDestinations.route,
            modifier = modifier
        ) {
            composable(route = HomeDestinations.route) {
                HomeBody(navigateToWordBook = {
                    navController.navigate(WordBookDestinations.route)
                }, navigateToTodo = {
                    navController.navigate(TodoDestinations.route)
                })
            }

            composable(route = WordBookDestinations.route) {
                WordBookScreen()
            }

            composable(route = TodoDestinations.route) {
                TodoScreen()
            }
        }
    }
    TabBar()
}

@Preview
@Composable
private fun HomeNavHostPreview() {
    HomeNavHost(navController = rememberNavController())
}