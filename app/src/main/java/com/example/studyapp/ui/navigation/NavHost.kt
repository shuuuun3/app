package com.example.studyapp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.ui.TabBar
import com.example.studyapp.ui.functions.AccountDestinations
import com.example.studyapp.ui.functions.AccountScreen
import com.example.studyapp.ui.functions.CalendarDestinations
import com.example.studyapp.ui.functions.CalendarScreen
import com.example.studyapp.ui.functions.SettingsDestinations
import com.example.studyapp.ui.functions.SettingsScreen
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
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    Box {
        Column {
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

                composable(route = CalendarDestinations.route) {
                    CalendarScreen()
                }

                composable(route = AccountDestinations.route) {
                    AccountScreen()
                }

                composable(route = SettingsDestinations.route) {
                    SettingsScreen()
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                TabBar(
                    selectedIndex = selectedIndex,
                    onSelectedIndexChanged = {newIndex ->
                        selectedIndex = newIndex
                        when (newIndex) {
                            0 -> navController.navigate(HomeDestinations.route)
                            1 -> navController.navigate(CalendarDestinations.route)
                            2 -> navController.navigate(AccountDestinations.route)
                            3 -> navController.navigate(SettingsDestinations.route)
                        }
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}


@Preview
@Composable
private fun HomeNavHostPreview() {
    HomeNavHost(navController = rememberNavController())
}