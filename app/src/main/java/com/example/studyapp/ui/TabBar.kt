package com.example.studyapp.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R

@Composable
fun TabBar(
    selectedIndex: Int = 0,
    onSelectedIndexChanged: (Int) -> Unit = {},
    /* navigateToHome: () -> Unit = {},
     navigateToCalendar: () -> Unit = {},
     navigateToAccount: () -> Unit = {},
     navigateToSettings: () -> Unit = {}*/
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val boxWidth = 55.dp
    val selectedBoxWidth = 133.dp
    val tabIconList = listOf(
        R.drawable.tab_home,
        R.drawable.tab_calendar,
        R.drawable.tab_account,
        R.drawable.tab_settings
    )
    val tabTextList = listOf(
        "Home",
        "Calendar",
        "Account",
        "Settings"
    )
    /*val navigateToFunctionsList = listOf(
        navigateToHome,
        navigateToCalendar,
        navigateToAccount,
        navigateToSettings
    )*/

    Box(
        modifier = Modifier
            .size(330.dp, 65.dp)
            .clip(RoundedCornerShape(100))
            .background(Color(0xff2B2B2B))
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .width(if (index == selectedIndex) selectedBoxWidth else boxWidth)
                        .height(55.dp)
                        .clip(RoundedCornerShape(100))
                        .background(Color(if (index == selectedIndex) 0xff6495ED else 0xff323232))
                        .clickable {
                            selectedIndex = index
                            onSelectedIndexChanged(index)
                        }
                        //アニメーションが適用されない *要改善
                        .animateContentSize(
                            animationSpec = tween(durationMillis = 300),
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val tintColor = if (index == selectedIndex) Color(0xffffffff) else Color(0xffA9A9A9)
                        Image(
                            painter = painterResource(id = tabIconList[index]),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(tintColor),
                            modifier = Modifier.size(18.dp)
                        )
                        if (selectedIndex == index) {
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                tabTextList[index],
                                fontSize = 15.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TabBarPreview() {
    TabBar()
}