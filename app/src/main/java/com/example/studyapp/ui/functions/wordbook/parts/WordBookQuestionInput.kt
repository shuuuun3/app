package com.example.studyapp.ui.functions.wordbook.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyapp.R
import com.example.studyapp.ui.functions.wordbook.WordBookViewModel
import com.example.studyapp.ui.functions.wordbook.WordBookViewModelProvider
import com.example.studyapp.ui.theme.StudyAppTheme

@Composable
fun WordBookQuestionInput(
    viewModel: WordBookViewModel = viewModel(factory = WordBookViewModelProvider.Factory),
    vocabularyId: Int,
    onClick: () -> Unit = {}
) {
    StudyAppTheme {
        var questionText: String by remember { mutableStateOf("") }
        var questionType: String by remember { mutableStateOf("pair") }
        var answerText: String by remember { mutableStateOf("") }
        var answerCompletion: List<String> by remember { mutableStateOf(emptyList()) }
        var answerChoices: List<String> by remember { mutableStateOf(emptyList()) }

        var isPopupVisible by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(580.dp)
                .clip(RoundedCornerShape(5))
                .background(Color(0xFF2B2B2B))
                .clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = null
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.cross),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {  }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Create Question",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )

            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Question Type",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xff344665),
                                    shape = RoundedCornerShape(20)
                                )
                                .clickable { isPopupVisible = true }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp, 0.dp)
                            ){
                                var type: String = when (questionType) {
                                    "pair" -> "Description"
                                    "completion" -> "Completion"
                                    "choice" -> "Selection"
                                    else -> ""
                                }
                                Text(
                                    text = type,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.arrow_down),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color(0xff525252)),
                                    modifier = Modifier
                                        .size(15.dp)
                                )
                            }
                        }
                        if (isPopupVisible) {
                            Popup(
                                alignment = Alignment.TopEnd,
                                onDismissRequest = { isPopupVisible = false },
                                properties = PopupProperties(focusable = true),
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, Color(0xff525252), RoundedCornerShape(5))
                                        .background(Color(0xFF2B2B2B))
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFF2B2B2B))
                                    ) {
                                        Text(
                                            text = "description",
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .clickable {
                                                    isPopupVisible = false
                                                    questionType = "pair"
                                                }
                                        )
                                        Text(
                                            text = "completion",
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .clickable {
                                                    isPopupVisible = false
                                                    questionType = "completion"
                                                }
                                        )
                                        Text(
                                            text = "selection",
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .clickable {
                                                    isPopupVisible = false
                                                    questionType = "choice"
                                                }
                                        )
                                    }
                                }
                            }
                        }

                        Row {
                            Text(
                                text = "Question",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Required",
                                fontSize = 11.sp,
                                color = Color(0xffAA3E3E),
                                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                            )
                        }
                        OutlinedTextField(
                            value = questionText,
                            placeholder = {
                                Text(
                                    text = "Enter question",
                                    color = Color(0xff525252),
                                    modifier = Modifier.padding(top = 2.dp, start = 5.dp)
                                )
                            },
                            onValueChange = { newText -> questionText = newText },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xff344665),
                                unfocusedBorderColor = Color(0xff525252)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clip(RoundedCornerShape(5))
                        )

                        when (questionType) {
                            "pair" -> {
                                Row(
                                    modifier = Modifier.padding(top = 10.dp)
                                ) {
                                    Text(
                                        text = "Answer",
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(top = 15.dp, bottom = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Required",
                                        fontSize = 11.sp,
                                        color = Color(0xffAA3E3E),
                                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                                    )
                                }
                                OutlinedTextField(
                                    value = answerText,
                                    placeholder = {
                                        Text(
                                            text = "Enter answer",
                                            color = Color(0xff525252),
                                            modifier = Modifier.padding(top = 2.dp, start = 5.dp)
                                        )
                                    },
                                    onValueChange = { newText -> answerText = newText },
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

                            "completion" -> {
                                var inputQuantity: Int by remember { mutableStateOf(2) }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 10.dp)
                                ) {
                                    Text(
                                        text = "Answer",
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Required",
                                        fontSize = 11.sp,
                                        color = Color(0xffAA3E3E),
                                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    WordBookSmallButton(
                                        imageId = R.drawable.add,
                                        text = "add",
                                        width = 70, height =25,
                                        onClick = {
                                            inputQuantity++
                                        }
                                    )
                                }

                                for (i in 1..inputQuantity) {
                                    CanDeleteAnswerInput(
                                        onClick = {
                                            if (inputQuantity >= 3) {
                                                inputQuantity--
                                            }
                                        },
                                        onInputChanged = { newText ->
                                            answerCompletion.toMutableList().apply {
                                                if (i <= this.size) this[i - 1] = newText
                                                else add(newText)
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }

                            "choice" -> {
                                Row(
                                    modifier = Modifier.padding(top = 10.dp)
                                ) {
                                    Text(
                                        text = "Answer",
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Required",
                                        fontSize = 11.sp,
                                        color = Color(0xffAA3E3E),
                                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                                    )
                                }
                                OutlinedTextField(
                                    value = answerText,
                                    placeholder = {
                                        Text(
                                            text = "Enter answer",
                                            color = Color(0xff525252),
                                            modifier = Modifier.padding(top = 2.dp, start = 5.dp)
                                        )
                                    },
                                    onValueChange = { newText -> answerText = newText },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xff344665),
                                        unfocusedBorderColor = Color(0xff525252)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .clip(RoundedCornerShape(5))
                                )

                                var inputQuantity: Int by remember { mutableStateOf(2) }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 10.dp)
                                ) {
                                    Text(
                                        text = "Uncorrected Answer",
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Required",
                                        fontSize = 11.sp,
                                        color = Color(0xffAA3E3E),
                                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    WordBookSmallButton(
                                        imageId = R.drawable.add,
                                        text = "add",
                                        width = 70, height =25,
                                        onClick = {
                                            inputQuantity++
                                        }
                                    )
                                }

                                for (i in 1..inputQuantity) {
                                    CanDeleteAnswerInput(
                                        onClick = {
                                            if (inputQuantity >= 3) {
                                                inputQuantity--
                                            }
                                        },
                                        onInputChanged = { newText ->
                                            answerChoices.toMutableList().apply {
                                                if (i <= this.size) this[i - 1] = newText
                                                else add(newText)
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        }
                    }
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(40.dp, 0.dp, 40.dp, 20.dp)
                    .clip(RoundedCornerShape(30))
                    .background(Color(0xff6495ED))
                    .clickable {
                        viewModel.addQuestion(
                            vocabularyId = vocabularyId,
                            questionText = questionText,
                            questionType = questionType,
                            answerText = answerText,
                            answerCompletion = answerCompletion,
                            answerChoices = answerChoices
                        )
                        onClick()
                    }
            ) {
                Text(
                    text = "Create",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun CanDeleteAnswerInput(
    onClick: () -> Unit = {},
    description: String = "",
    onInputChanged: (String) -> Unit = {}
) {

    var inputText by remember { mutableStateOf("") }

    StudyAppTheme {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = inputText,
                placeholder = {
                    Text(
                        text = description,
                        color = Color(0xff525252),
                        modifier = Modifier.padding(top = 2.dp, start = 5.dp)
                    )
                },
                onValueChange = { newText ->
                    inputText = newText
                    onInputChanged(newText)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff344665),
                    unfocusedBorderColor = Color(0xff525252)
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .clip(RoundedCornerShape(5))
            )
            Box(modifier = Modifier.padding(10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.cross),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .size(34.dp)
                        .clickable { onClick() }
                )
            }
        }
    }
}
