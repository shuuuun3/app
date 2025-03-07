package com.example.studyapp.ui.functions.wordbook.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyapp.R
import com.example.studyapp.data.WordBookViewModel
import com.example.studyapp.data.WordBookViewModelProvider
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookVocabularyInput(
    viewModel: WordBookViewModel = viewModel(factory = WordBookViewModelProvider.Factory),
    onClick: () -> Unit = {},
) {
    StudyAppTheme {
        var titleText: String by remember { mutableStateOf("") }
        var descriptionText: String by remember { mutableStateOf("") }
        var iconColor: Long by remember { mutableLongStateOf(0xff0D99FF) }
        var showPopup by remember { mutableStateOf(false) }

        val iconColorList = listOf(0xff0D99FF, 0xffF44135, 0xff49AD4E, 0xffFE9700, 0xffFFE93A, 0xffe71c61, 0xff9d24b1, 0xff009487)
        Box(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .height(530.dp)
                .clip(RoundedCornerShape(5))
                .background(color = Color(0xFF2B2B2B))
                .clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = null
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 15.dp)
            ) {
                Column {
                    Text(
                        text = "Title",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedTextField(
                        value = titleText,
                        placeholder = {
                            Text(
                                text = "Enter title",
                                color = Color(0xff525252),
                                modifier = Modifier.padding(top = 2.dp, start = 5.dp)
                            )
                        },
                        onValueChange = { newText -> titleText = newText },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xff344665),
                            unfocusedBorderColor = Color(0xff525252)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(5))
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text(
                        text = "Description",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedTextField(
                        value = descriptionText,
                        placeholder = {
                            Text(
                                text = "Enter description",
                                color = Color(0xff525252),
                                modifier = Modifier.padding(top = 2.dp, start = 5.dp)
                            )
                        },
                        onValueChange = { newText -> descriptionText = newText },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xff344665),
                            unfocusedBorderColor = Color(0xff525252)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(5))
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Icon",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Box (
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Transparent)
                            .border(2.dp, Color(0xff525252), RoundedCornerShape(8))
                            .align(Alignment.CenterHorizontally)
                            .clickable { showPopup = true }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.wordbook_file),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color(iconColor)),
                            modifier = Modifier
                                .size(80.dp)
                                .align(Alignment.Center)
                        )
                        if (showPopup) {
                            Popup(
                                onDismissRequest = { showPopup = false }
                            ) {
                                Column (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(8))
                                        .background(Color(0xff2f2f2f))
                                ) {
                                    Text(
                                        text = "Icon Color",
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    iconColorList.chunked(5).forEach { colors ->
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly) {
                                            colors.forEach { color ->
                                                Box(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .size(80.dp)
                                                        .border(1.dp, Color(0xff525252), RoundedCornerShape(100))
                                                        .clickable {
                                                            iconColor = color
                                                            showPopup = false
                                                        }
                                                ) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.wordbook_file),
                                                        contentDescription = null,
                                                        colorFilter = ColorFilter.tint(Color(color)),
                                                        modifier = Modifier
                                                            .size(50.dp)
                                                            .align(Alignment.Center)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                WordBookBigButton(
                    text = "Create",
                    onClick = {
                        if (titleText != "") {
                            viewModel.addVocabulary(
                                title = titleText,
                                description = descriptionText,
                                iconColor = iconColor
                            )
                            onClick()
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun WordBookVocabularyInputPreview() {
    WordBookVocabularyInput()
}