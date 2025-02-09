package com.example.studyapp.ui.functions.startstudy.parts

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R
import com.example.studyapp.data.Subjects
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlin.math.roundToInt

@Composable
fun SelectSubject(
    subjectName: String,
    fontColor: Long,
    backgroundColor: Long,
    imageId: Int,
    onClick: () -> Unit = {},
    topRounded: Int = 100,
    bottomRounded: Int = 100,
    isExpanded: Boolean = false,
    height: Dp = 70.dp,
    isTop: Boolean = true
) {

    StudyAppTheme {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(364.dp)
                    .height(height)
                    .background(
                        Color(backgroundColor), shape = RoundedCornerShape(
                            topStartPercent = topRounded,
                            topEndPercent = topRounded,
                            bottomStartPercent = bottomRounded,
                            bottomEndPercent = bottomRounded
                        )
                    )
                    .clickable { onClick() }
            ) {
                Text(
                    text = subjectName,
                    fontSize = 18.sp,
                    color = Color(fontColor)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    if (isTop) {
                        Image(
                            if (isExpanded) {
                                painterResource(id = R.drawable.arrow_up)
                            } else {
                                painterResource(id = R.drawable.arrow_down)
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp),
                            colorFilter = ColorFilter.tint(Color(fontColor))
                        )
                    }else {
                        Box(Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(60.dp))
                    Image(
                        painterResource(id = imageId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(27.dp),
                        colorFilter = ColorFilter.tint(Color(fontColor))
                    )
                }
            }
        }
    }
}

@Composable
fun SelectedSubjectDropdown(
    subjectItems: List<Subjects> = listOf(),
    selectedSubject: Subjects,
    onSubjectSelected: (Subjects) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val unselectedSubjects = subjectItems.filterNot { it == selectedSubject }

    val animatedHeight by animateDpAsState(
        targetValue = if (isExpanded) 70.dp else 0.dp,
        animationSpec = tween(durationMillis = 300) // 300msのアニメーション
    )
    val animatedTopRounded by animateFloatAsState(
        targetValue = if (isExpanded) 50f else 100f,
        animationSpec = tween(durationMillis = 300) // 300msのアニメーション
    )
    val animatedBottomRounded by animateFloatAsState(
        targetValue = if (isExpanded) 0f else 100f,
        animationSpec = tween(durationMillis = 300) // 300msのアニメーション
    )

    Column {
        SelectSubject(
            subjectName = selectedSubject.name,
            fontColor = selectedSubject.color,
            backgroundColor = selectedSubject.backgroundColor,
            imageId = selectedSubject.imageId,
            topRounded = animatedTopRounded.roundToInt(),
            bottomRounded = animatedBottomRounded.roundToInt(),
            isExpanded = isExpanded,
            isTop = true,
            onClick = {
                isExpanded = !isExpanded
            }
        )
        unselectedSubjects.forEachIndexed{ index, item ->
            if (index == unselectedSubjects.lastIndex) {
                SelectSubject(
                    subjectName = item.name,
                    fontColor = item.color,
                    backgroundColor = item.backgroundColor,
                    imageId = item.imageId,
                    topRounded = 0,
                    bottomRounded = 50,
                    isTop = false,
                    height = animatedHeight,
                    onClick = {
                        onSubjectSelected(item)
                        isExpanded = false
                    }
                )
            }else {
                SelectSubject(
                    subjectName = item.name,
                    fontColor = item.color,
                    backgroundColor = item.backgroundColor,
                    imageId = item.imageId,
                    topRounded = 0,
                    bottomRounded = 0,
                    isTop = false,
                    height = animatedHeight,
                    onClick = {
                        onSubjectSelected(item)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SelectSubjectPreview() {
    SelectSubject(backgroundColor = 0xff394557, fontColor = 0xff8AB4F8, imageId = R.drawable.function, subjectName = "math")
}