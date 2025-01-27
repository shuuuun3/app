package com.example.studyapp.ui.functions.wordbook.parts

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.R
import com.example.studyapp.ui.theme.StudyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDefaults(
    title: String = "Home",
    canNavigateBack: Boolean = false,
    navigateBack: () -> Unit = {}
) {
    StudyAppTheme () {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                if (canNavigateBack) {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "null",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 20.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(180.dp)
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.topappbar_search),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(30.dp)
                            .clickable { }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.topappbar_sort),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(30.dp)
                            .clickable { }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.topappbar_fileplus),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(30.dp)
                            .clickable { }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.topappbar_more),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(30.dp)
                            .clickable { }
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0xff101010)
@Composable
private fun TopAppBarDefaultsPreview() {
    TopAppBarDefaults(canNavigateBack = true)
}

@Composable
fun TopAppBarVocabulary(
    title: String = "Vocabulary",
    canNavigateBack: Boolean = false,
    navigateBack: () -> Unit = {}
) {
    StudyAppTheme () {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                if (canNavigateBack) {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "null",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 20.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(180.dp)
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.topappbar_search),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(30.dp)
                            .clickable { }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.filtetlist),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(30.dp)
                            .clickable { }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.topappbar_sort),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(30.dp)
                            .clickable { }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.topappbar_more),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(30.dp)
                            .clickable { }
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0xff101010)
@Composable
private fun TopAppBarVocabularyPreview() {
    TopAppBarVocabulary(canNavigateBack = true, title = "Vocabulary")
}
