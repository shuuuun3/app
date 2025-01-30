package com.example.studyapp.ui.functions.startstudy

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R
import com.example.studyapp.ui.navigation.NavigationDestination
import com.example.studyapp.ui.theme.StudyAppTheme

object StartStudyDestinations: NavigationDestination {
    override val route = "starStudy"
    override val titleRes = R.string.entry_startStudy
}

@Composable
fun StartStudyScreen() {

}

@Composable
fun StarStudyBody() {
    StudyAppTheme {

    }
}

@Composable
fun TitleInput() {
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
                modifier = Modifier.fillMaxWidth().padding()
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

@Composable
fun SelectSubject(
    subjectName: String,
    fontColor: Long,
    backgroundColor: Long,
    onClick: () -> Unit = {}
) {
    StudyAppTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(364.dp)
                .height(70.dp)
                .background(Color(backgroundColor), shape = RoundedCornerShape(100))
                .clickable { onClick() }
        ) {
            Text(
                text = subjectName,
                fontSize = 18.sp,
                color = Color(fontColor)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painterResource(id = R.drawable.arrow_down),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                    colorFilter = ColorFilter.tint(Color(fontColor))
                )
                Spacer(modifier = Modifier.width(70.dp))
                Image(
                    painterResource(id = R.drawable.function),
                    contentDescription = null,
                    modifier = Modifier
                        .size(27.dp),
                    colorFilter = ColorFilter.tint(Color(fontColor))
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
private fun SelectSubjectPreview() {
    SelectSubject(backgroundColor = 0xff394557, fontColor = 0xff8AB4F8, subjectName = "数学")
}