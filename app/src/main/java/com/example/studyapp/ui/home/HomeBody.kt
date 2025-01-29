package com.example.studyapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.ui.AccountTopBar
import com.example.studyapp.ui.OutlinedButton
import com.example.studyapp.ui.navigation.NavigationDestination

object HomeDestinations : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeBody(
    userName: String = "Shunsuke",
    navigateToWordBook: () -> Unit = {},
    navigateToGraph: () -> Unit = {},
    navigateToTodo: () -> Unit = {},
    navigateToAddStudy: () -> Unit = {},
    navigateToStartStudy: () -> Unit = {}
) {
    Box() {
        Column(modifier = Modifier.background(Color(0xFF101010))) {
            AccountTopBar(
                text = "Function",
                showHello = true,
                userName = userName
            )
            Text(
                text = "週カレンダー追加予定地",
                modifier = Modifier
                    .padding(32.dp)
            )
            //functionsBlock↓
            Row() {
                Spacer(modifier = Modifier.weight(1f))
                ToFunctionItem(
                    title = "WordBook",
                    iconPath = R.drawable.home_item_wordbook,
                    navigateToFunction = navigateToWordBook
                )
                Spacer(modifier = Modifier.weight(1f))
                ToFunctionItem(
                    title = "Graph",
                    iconPath = R.drawable.graph,
                    navigateToFunction = navigateToGraph
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ToFunctionItem(
                    title = "Todo",
                    iconPath = R.drawable.todo,
                    navigateToFunction = navigateToTodo
                )
                Spacer(modifier = Modifier.weight(1f))
                ToFunctionItem(
                    title = "Add Study",
                    iconPath = R.drawable.home_item_addstudy,
                    navigateToFunction = navigateToAddStudy
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ToFunctionItem(
                    title = "Start Study",
                    iconPath = R.drawable.home_item_startstudy,
                    navigateToFunction = navigateToStartStudy
                )
                Spacer(modifier = Modifier.weight(1f))
                ToFunctionItem(
                    title = "Add Study",
                    iconPath = R.drawable.home_item_addstudy,
                    navigateToFunction = navigateToAddStudy
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.padding(top = 82.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment =  Alignment.CenterHorizontally
            ) {
                OutlinedButton(text = "Quick Start")
            }
        }
    }
}

@Preview
@Composable
private fun HomeBodyPreview() {
    HomeBody(userName = "Shunsuke")
}