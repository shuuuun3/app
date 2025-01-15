package com.example.studyapp.ui.functions.graph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyapp.R
import com.example.studyapp.ui.navigation.NavigationDestination

object GraphDestinations : NavigationDestination {
    override val route = "graph"
    override val titleRes = R.string.entry_graph
}

@Composable
fun GraphScreen() {
    Text(text = "Graph")
}

@Preview
@Composable
private fun GraphScreenPreview() {
    GraphScreen()
}