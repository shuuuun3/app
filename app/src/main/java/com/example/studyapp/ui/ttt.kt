package com.example.studyapp.ui

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyapp.R

@Composable

fun SearchIcon() {

    Image(

        painter = painterResource(id = R.drawable.topappbar_search),

        contentDescription = null,

        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),

        )

}



@Preview

@Composable

private fun SearchIconPreview() {

    SearchIcon()

}