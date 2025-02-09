package com.example.studyapp.ui.functions.startstudy

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyapp.R
import com.example.studyapp.data.StartStudyViewModel
import com.example.studyapp.data.StartStudyViewModelProvider
import com.example.studyapp.data.StudyTimes
import com.example.studyapp.data.Subjects
import com.example.studyapp.ui.OutlinedButton
import com.example.studyapp.ui.functions.startstudy.parts.SelectedSubjectDropdown
import com.example.studyapp.ui.functions.startstudy.parts.TimeSelector
import com.example.studyapp.ui.functions.startstudy.parts.TitleInput
import com.example.studyapp.ui.navigation.NavigationDestination
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

object StartStudyDestinations: NavigationDestination {
    override val route = "startStudy"
    override val titleRes = R.string.entry_startStudy
}

@Composable
fun StartStudyScreen(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    navigateToTimer: (String, Int, Int, Int) -> Unit
) {
    val StartStudyUiState by viewModel.startStudyUiState.collectAsState()

    if (StartStudyUiState.isLoading) {
        CircularProgressIndicator() // ローディング中はインジケーターを表示
    } else {
        val subjectItems = StartStudyUiState.subjectList.collectAsState(initial = listOf()).value
        val studyTimesItems = StartStudyUiState.studyTimesList.collectAsState(initial = listOf()).value

        LaunchedEffect(subjectItems) {
            Log.d("StartStudyScreen", "Subjects in UI: $subjectItems")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            StartStudyBody(
                subjectItems = subjectItems,
                studyTimesItems = studyTimesItems,
                navigateToTimer = navigateToTimer
            )
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun StartStudyBody(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    subjectItems: List<Subjects> = listOf(),
    studyTimesItems: List<StudyTimes> = listOf(),
    navigateToTimer: (String, Int, Int, Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var selectedSubject by remember {
        mutableStateOf(Subjects(1, "math", R.drawable.function, 0xff8AB4F8, 0xff394557))
    }
    var title by remember { mutableStateOf("") }
    var selectedTime by remember { mutableIntStateOf(10) }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(selectedSubject) {
        Log.d("StartStudyBody", "Selected Subject: $selectedSubject")
        if (subjectItems.isNotEmpty() && selectedSubject !in subjectItems) {
            selectedSubject = subjectItems.first()
        }
    }

    StudyAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp

            Box(
                modifier = Modifier
                    .offset(x = (screenWidth - 364.dp) / 2, y = 60.dp)
                    .zIndex(1f)
            )
            {
                SelectedSubjectDropdown(
                    subjectItems = subjectItems,
                    selectedSubject = selectedSubject,
                    onSubjectSelected = { newSubject ->
                        selectedSubject = newSubject
                    }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Spacer(modifier = Modifier.height(70.dp))
                Spacer(modifier = Modifier.height(30.dp))
                TitleInput(
                    onInputChanged = { newTitle ->
                        title = newTitle
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                TimeSelector(
                    studyTimesItems = studyTimesItems,
                    onTimeSelected = { newTime ->
                        selectedTime = newTime
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .width(364.dp)
                        .height(260.dp)
                        .clip(RoundedCornerShape(10))
                        .background(Color((0xff202020)))
                ) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { newDescription ->
                            description = newDescription
                        },
                        placeholder = {
                            Text(
                                text = "description",
                                color = Color(0xff525252)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedButton(
                    text = "Start",
                    onClick = {
                        if (title != "") {
                            coroutineScope.launch {
                                val studyRecordId =viewModel.addStudyRecord(
                                    subjectId = selectedSubject.subjectId,
                                    title = title,
                                    description = description,
                                    studiedTime = null,
                                    studyDate = LocalDate.now()
                                )
                                // studyRecordId を渡してO画面遷移
                                navigateToTimer("startStudy", selectedSubject.subjectId, selectedTime, studyRecordId)
                            }
                        } else{
                            Log.d("StartStudyBody", "Title is empty")
                        }
                    }
                )
            }
        }
    }
}
