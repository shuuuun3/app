@file:Suppress("NAME_SHADOWING")

package com.example.studyapp.ui.navigation

import android.util.Log
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
import com.example.studyapp.ui.functions.studyInput.addstudy.AddStudyDestinations
import com.example.studyapp.ui.functions.studyInput.addstudy.AddStudyScreen
import com.example.studyapp.ui.functions.calendar.CalendarDestinations
import com.example.studyapp.ui.functions.calendar.CalendarScreen
import com.example.studyapp.ui.functions.graph.GraphDestinations
import com.example.studyapp.ui.functions.graph.GraphScreen
import com.example.studyapp.ui.functions.settings.SettingsDestinations
import com.example.studyapp.ui.functions.settings.SettingsScreen
import com.example.studyapp.ui.functions.studyInput.startstudy.StartStudyDestinations
import com.example.studyapp.ui.functions.studyInput.startstudy.StartStudyScreen
import com.example.studyapp.ui.functions.timer.TimerFinishScreen
import com.example.studyapp.ui.functions.timer.TimerScreen
import com.example.studyapp.ui.functions.todo.TodoDestinations
import com.example.studyapp.ui.functions.todo.TodoScreen
import com.example.studyapp.ui.functions.wordbook.WordBookDestinations
import com.example.studyapp.ui.functions.wordbook.WordBookScreen
import com.example.studyapp.ui.functions.wordbook.WordBookVocabulary
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
                    }, navigateToStartStudy = {
                        navController.navigate(StartStudyDestinations.route)
                    }, navigateToPomodoroTimer = { type, studyRecordId ->
                        if (studyRecordId > 0) {
                            navController.navigate("TimerDestinations.route/$type/$studyRecordId")
                        } else {
                            // 無効な ID の場合、画面遷移を避ける
                            Log.e("Navigation", "Invalid studyRecordId: $studyRecordId")
                        }
                    })
                }

                composable(route = "TimerDestinations.route/{type}/{studyRecordId}") { backStackEntry ->
                    val type = backStackEntry.arguments?.getString("type") ?: "pomodoroTimer"
                    val studyRecordId = backStackEntry.arguments?.getString("studyRecordId")?.toIntOrNull() ?: 0
                    if (studyRecordId > 0) {
                        TimerScreen(
                            timerType = type,
                            subjectId = null,
                            studyRecordId = studyRecordId,
                            navigateToFinishScreen = { type, subjectId, studyRecordId ->
                                navController.navigate("FinishScreenDestinations.route/$type/$subjectId/$studyRecordId")
                            }
                        )
                    } else {
                        // 無効な studyRecordId の場合のエラーハンドリング
                        Log.e("TimerScreen", "Invalid studyRecordId: $studyRecordId")
                    }
                }

                composable(route = "FinishScreenDestinations.route/{type}/{subjectId}/{studyRecordId}") { backStackEntry ->
                    val type = backStackEntry.arguments?.getString("type") ?: "pomodoroTimer"
                    val subjectId = backStackEntry.arguments?.getString("subjectId")?.toIntOrNull()
                    val studyRecordId = backStackEntry.arguments?.getString("studyRecordId")?.toIntOrNull() ?: 0
                    TimerFinishScreen(
                        timerType = type,
                        subjectId = subjectId,
                        studyRecordId = studyRecordId,
                        navigateToHome = {
                            navController.navigate(HomeDestinations.route)
                        }
                    )
                }

                composable(route = WordBookDestinations.route) {
                    WordBookScreen(navigateToVocabulary = { id, title ->
                        navController.navigate("WordBookDestinations.route/$id/$title")
                    })
                }

                composable(route = "WordBookDestinations.route/{id}/{title}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
                    val title = backStackEntry.arguments?.getString("title") ?: "Unknown"
                    WordBookVocabulary(
                        vocabularyId = id,
                        title = title,
                        navigateBack = {
                            navController.navigate(WordBookDestinations.route)
                        }
                    )
                }

                composable(route = StartStudyDestinations.route) {
                    StartStudyScreen(navigateToTimer = { type, subjectId, timeValueInMinutes, studyRecordId ->
                        navController.navigate("TimerDestinations.route/$type/$subjectId/$timeValueInMinutes/$studyRecordId")
                    })
                }

                composable(route = "TimerDestinations.route/{type}/{subjectId}/{timeValueInMinutes}/{studyRecordId}") { backStackEntry ->
                    val type = backStackEntry.arguments?.getString("type") ?: "startStudy"
                    val subjectId = backStackEntry.arguments?.getString("subjectId")?.toIntOrNull() ?: 0
                    val timeValueInMinutes = backStackEntry.arguments?.getString("timeValueInMinutes")?.toIntOrNull() ?: 0
                    val studyRecordId = backStackEntry.arguments?.getString("studyRecordId")?.toIntOrNull() ?: 0
                    TimerScreen(
                        timerType = type,
                        subjectId = subjectId,
                        studyRecordId = studyRecordId,
                        timerValueInMinutes = timeValueInMinutes,
                        navigateToFinishScreen = { type, subjectId, studyRecordId ->
                            navController.navigate("FinishScreenDestinations.route/$type/$subjectId/$studyRecordId")
                        }
                    )
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