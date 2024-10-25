package com.example.studyapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SubjectSelection(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedSubject by remember { mutableStateOf("数学") }
    var subjects = listOf("数学", "英語", "国語")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true },
    ) {
        Text(
            text = selectedSubject,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {

        }
    }
}

@Preview(showBackground = true,
    backgroundColor = 0xFF)
@Composable
private fun SubjectSelectionPreview() {
    SubjectSelection()
}

@Composable
fun MyDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selectedSubject by remember { mutableStateOf("数学") }
    var subjects = listOf("数学", "英語", "国語")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xff394557), shape = MaterialTheme.shapes.extraLarge)
            .clickable { expanded = true }
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Dropdown arrow",
            modifier = Modifier
                .padding(16.dp),
            tint = Color(0xff8AB4F8)
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Icon(painter = painterResource(id = R.drawable.round_functions_24),
                contentDescription = "mathIcon",
                tint = Color(0xff8AB4F8),
                modifier = Modifier
                    .padding(end = 100.dp)
                    .align(Alignment.Center)
            )
            Text(
                text = selectedSubject,
                fontSize = 16.sp,
                color = Color(0xff8AB4F8),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun MyDropdownMenuPreview() {
    MyDropdownMenu()
}