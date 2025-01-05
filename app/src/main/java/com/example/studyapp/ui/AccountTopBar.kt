package com.example.studyapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R

@Composable
fun AccountTopBar(
    text: String,
    showHello: Boolean,
    userName: String
) {
    Box(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.account_circle),
                        contentDescription = "AccountIcon",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                if (showHello) {
                    Text(
                        text = buildAnnotatedString {
                            append("Hello,")
                            appendLine()
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(userName)
                            }
                        },
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xff313142), shape = CircleShape)
                        .clickable { /*通知表示*/ },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.notice_bell),
                        contentDescription = "Notification",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Text(
                text = text,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun AccountTopBarPreview(showBackground: Boolean = true) {
    AccountTopBar(text = "Account", showHello = true, userName = "Shunsuke")
}


//画像を丸く切り取るコード
/*
@Composable
fun image(modifier: Modifier = Modifier) {
    Image(painter = painterResource(id = R.drawable.tnimg_j470_15), contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
    )
}

@Preview
@Composable
private fun imagepreview() {
    image()
}*/
