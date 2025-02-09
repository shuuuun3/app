package com.example.studyapp.ui.functions.startstudy.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.ui.theme.StudyAppTheme

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