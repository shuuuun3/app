package com.example.studyapp.ui.functions.startstudy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyapp.R
import com.example.studyapp.data.AppRepository
import com.example.studyapp.data.ChoiceAnswerEntity
import com.example.studyapp.data.CompletionAnswerEntity
import com.example.studyapp.data.PairAnswerEntity
import com.example.studyapp.data.QuestionEntity
import com.example.studyapp.data.QuestionWithAnswers
import com.example.studyapp.data.StartStudyViewModel
import com.example.studyapp.data.StartStudyViewModelProvider
import com.example.studyapp.data.Subjects
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.navigation.NavigationDestination
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.flow.Flow

object StartStudyDestinations: NavigationDestination {
    override val route = "startStudy"
    override val titleRes = R.string.entry_startStudy
}

@Composable
fun StartStudyScreen(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory)
) {
    val StartStudyUiState by viewModel.startStudyUiState.collectAsState()
    val subjectItems = StartStudyUiState.subjectList.collectAsState(initial = listOf()).value
    StartStudyBody(
        subjectItems = subjectItems
    )
}


@Composable
fun StartStudyBody(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    subjectItems: List<Subjects> = listOf()
) {
    var selectedSubjectName by remember { mutableStateOf("noValue") }

    StudyAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                SelectedSubjectDropdown(
                    onSubjectSelected = { selectedSubject ->
                        selectedSubjectName = selectedSubject.name
                    }
                )
                TitleInput()
            }
        }
    }
}

@Composable
fun TitleInput(
    onInputChanged: (String) -> Unit = {}
) {
    StudyAppTheme {
        var inputText by remember { mutableStateOf("") }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(364.dp)
                .height(70.dp)
                .background(Color(0xff202020), shape = RoundedCornerShape(100))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painterResource(R.drawable.textinput),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                    colorFilter = ColorFilter.tint(Color(0xff434343))
                )
                OutlinedTextField(
                    value = inputText,
                    placeholder = {
                        Text(
                            text = "title",
                            color = Color(0xff525252),
                            modifier = Modifier.padding(top = 2.dp, start = 100.dp)
                        )
                    },
                    onValueChange = { newText ->
                        inputText = newText
                        onInputChanged(newText)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleInputPreview() {
    TitleInput()
}