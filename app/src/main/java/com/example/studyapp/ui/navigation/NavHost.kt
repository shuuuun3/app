package com.example.studyapp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.ui.TabBar
import com.example.studyapp.ui.functions.account.AccountDestinations
import com.example.studyapp.ui.functions.account.AccountScreen
import com.example.studyapp.ui.functions.addstudy.AddStudyDestinations
import com.example.studyapp.ui.functions.addstudy.AddStudyScreen
import com.example.studyapp.ui.functions.calendar.CalendarDestinations
import com.example.studyapp.ui.functions.calendar.CalendarScreen
import com.example.studyapp.ui.functions.graph.GraphDestinations
import com.example.studyapp.ui.functions.graph.GraphScreen
import com.example.studyapp.ui.functions.settings.SettingsDestinations
import com.example.studyapp.ui.functions.settings.SettingsScreen
import com.example.studyapp.ui.functions.todo.TodoDestinations
import com.example.studyapp.ui.functions.todo.TodoScreen
import com.example.studyapp.ui.functions.wordbook.WordBookDestinations
import com.example.studyapp.ui.functions.wordbook.WordBookScreen
import com.example.studyapp.ui.home.HomeBody
import com.example.studyapp.ui.home.HomeDestinations


@Composable
fun HomeNavHost(
    navController: NavHostController
) {
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    Box {
        Column {
            NavHost(
                navController = navController,
                startDestination = HomeDestinations.route,
                modifier = Modifier.weight(1f)
            ) {
                composable(route = HomeDestinations.route) {
                    HomeBody(navigateToWordBook = {
                        navController.navigate(WordBookDestinations.route)
                    }, navigateToTodo = {
                        navController.navigate(TodoDestinations.route)
                    }, navigateToGraph = {
                        navController.navigate(GraphDestinations.route)
                    }, navigateToAddStudy = {
                        navController.navigate(AddStudyDestinations.route)
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

                composable(route = GraphDestinations.route) {
                    GraphScreen()
                }

                composable(route = AddStudyDestinations.route) {
                    AddStudyScreen()
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
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