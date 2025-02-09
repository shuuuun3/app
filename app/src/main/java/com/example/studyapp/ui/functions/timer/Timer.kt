package com.example.studyapp.ui.functions.timer

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
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
import com.example.studyapp.data.StudyRecords
import com.example.studyapp.data.StudyTimes
import com.example.studyapp.data.Subjects
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.LongPressButtonWithProgress
import com.example.studyapp.ui.OutlinedButton
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TimerScreen(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    timerType: String,
    subjectId: Int?,
    studyRecordId: Int,
    timerValueInMinutes: Int = 10,
    breakTimeInMinutes: Int = 5,
    navigateToHome: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    // 選択された科目を取得する
    val subjectItem = remember(subjectId) {
        mutableStateOf<Subjects?>(null)
    }
    LaunchedEffect(subjectId) {
        subjectId?.let {
            subjectItem.value = viewModel.getSubjectsById(it)
        }
    }

    val date = LocalDate.now()
    val dayOfWeek = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

    when (timerType) {
        "pomodoroTimer" -> {
            // ★ ポモドーロ・モード（ポモドーロテクニック）
            // 初回は 25 分の勉強タイマーを自動起動し、
            // 25 分経過で自動的にポップアップ表示と 5 分のブレイクタイマー開始、
            // 5 分経過で自動的に 25 分タイマーを再開（以降ループ）、
            // ブレイク開始ボタンは表示しない
            // 1 秒ごとに総経過時間（pomodoroTotalTime）を更新・表示する

            // 定数（秒単位）
            val studyDuration = 25 * 60
            val breakDuration = 5 * 60

            // 現在のフェーズが勉強か休憩か（true: Study / false: Break）
            var isStudyPhase by remember { mutableStateOf(true) }
            // 残り秒数（フェーズ毎にリセット）
            var pomodoroRemainingTime by remember { mutableStateOf(studyDuration) }
            // 総経過時間（秒）
            var pomodoroTotalTime by remember { mutableStateOf(0L) }
            // 休憩時はポップアップ表示する
            var showBreakPopup by remember { mutableStateOf(false) }

            // 1秒ごとに更新するループ（フェーズの自動切替）
            LaunchedEffect(Unit) {
                while (true) {
                    delay(1000L)
                    pomodoroTotalTime++  // 総経過時間更新
                    if (pomodoroRemainingTime > 0) {
                        pomodoroRemainingTime--
                    } else {
                        // タイマー終了時の処理
                        if (isStudyPhase) {
                            // 勉強フェーズ終了 → 休憩フェーズへ切替え＆ポップアップ表示
                            showBreakPopup = true
                            isStudyPhase = false
                            pomodoroRemainingTime = breakDuration
                        } else {
                            // 休憩フェーズ終了 → ポップアップ非表示＆勉強フェーズに戻す
                            showBreakPopup = false
                            isStudyPhase = true
                            pomodoroRemainingTime = studyDuration
                        }
                    }
                }
            }

            // 表示用の分・秒
            val minutes = pomodoroRemainingTime / 60
            val seconds = pomodoroRemainingTime % 60

            StudyAppTheme {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(70.dp))
                        Text(
                            text = "$date, $dayOfWeek",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        // 科目の画像表示（subjectItemが取得できていれば）
                        subjectItem.value?.let { subject ->
                            Image(
                                painter = painterResource(id = subject.imageId),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color(subject.color)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(80.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                        // 現在のフェーズを表示（任意）
                        Text(
                            text = if (isStudyPhase) "Study" else "Break",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        // カウントダウンタイマー表示
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.width(55.dp))
                            Text(
                                text = minutes.toString().padStart(2, '0'),
                                fontSize = 100.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End
                            )
                            Text(
                                text = ":",
                                fontSize = 100.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(0.5f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = seconds.toString().padStart(2, '0'),
                                fontSize = 100.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.width(55.dp))
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        // 総経過時間表示（秒数）
                        val totalHours = pomodoroTotalTime / 3600
                        val totalMinutes = pomodoroTotalTime % 3600 / 60
                        val totalSeconds = pomodoroTotalTime % 60
                        Text(
                            text = "Total: $totalHours:$totalMinutes:$totalSeconds",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        // プログレスバー（フェーズに応じた最大値を設定）
                        LeftTimeBar(
                            originalTime = if (isStudyPhase) studyDuration * 1000L else breakDuration * 1000L,
                            leftTime = pomodoroRemainingTime * 1000L
                        )
                        Spacer(modifier = Modifier.height(90.dp))
                        // Finishボタン：タイマーを終了して学習記録更新後、ホームへ遷移
                        LongPressButtonWithProgress(
                            text = "Finish",
                            buttonWidth = 210,
                            color = Color(0xffDF7163),
                            onClick = {
                                // 必要に応じてループ中のタイマー（LaunchedEffect）は破棄される
                                coroutineScope.launch {
                                    viewModel.updateStudyRecord(
                                        studyRecordId = studyRecordId,
                                        studiedTime = pomodoroTotalTime.toInt()
                                    )
                                }
                                navigateToHome()
                            }
                        )
                    }
                    // 休憩フェーズ中はポップアップ表示（自動開始・自動終了）
                    if (showBreakPopup) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .offset(y = (30).dp)
                                    .width(300.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.popup),
                                    contentDescription = null,
                                )
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                ) {
                                    Text(
                                        text = "Break Time",
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(30.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(65.dp))
                                        Text(
                                            text = minutes.toString().padStart(2, '0'),
                                            fontSize = 60.sp,
                                            fontWeight = FontWeight.Light,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.weight(1f),
                                            textAlign = TextAlign.End
                                        )
                                        Text(
                                            text = ":",
                                            fontSize = 60.sp,
                                            fontWeight = FontWeight.Light,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.weight(0.5f),
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = seconds.toString().padStart(2, '0'),
                                            fontSize = 60.sp,
                                            fontWeight = FontWeight.Light,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.weight(1f),
                                            textAlign = TextAlign.Start
                                        )
                                        Spacer(modifier = Modifier.width(65.dp))
                                    }
                                    // ポモドーロ・モードでは休憩開始ボタンは表示しません
                                }
                            }
                        }
                    }
                }
            }
        }

        "startStudy" -> {
            // ★ 通常タイマー・モードの処理（既存コード）
            // 秒数で保持
            var totalTimeInSeconds by remember { mutableStateOf(0L) }
            var elapsedTime by remember { mutableStateOf(0L) }
            val remainingTime = remember { mutableStateOf(timerValueInMinutes * 60) }
            val timer = remember {
                CoroutineTimer(intervalMillis = 1000) { timeLeft ->
                    remainingTime.value = (timeLeft / 1000).toInt()
                    elapsedTime = (timerValueInMinutes * 60) - (timeLeft / 1000)
                }
            }
            LaunchedEffect(timerValueInMinutes) {
                timer.start(timerValueInMinutes * 60 * 1000L)
            }
            val minutes = remainingTime.value / 60
            val seconds = remainingTime.value % 60
            val showPopup = remember { mutableStateOf(false) }

            subjectItem.value?.let { subject ->
                StudyAppTheme {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(70.dp))
                            Text(
                                text = "$date, $dayOfWeek",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Image(
                                painter = painterResource(id = subject.imageId),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color(subject.color)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(80.dp)
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.width(55.dp))
                                Text(
                                    text = minutes.toString().padStart(2, '0'),
                                    fontSize = 100.sp,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                                Text(
                                    text = ":",
                                    fontSize = 100.sp,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(0.5f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = seconds.toString().padStart(2, '0'),
                                    fontSize = 100.sp,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start
                                )
                                Spacer(modifier = Modifier.width(55.dp))
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            LeftTimeBar(
                                timerValueInMinutes * 60 * 1000L,
                                remainingTime.value * 1000L
                            )
                            Spacer(modifier = Modifier.height(90.dp))
                            Box {
                                if (showPopup.value) {
                                    val breakTimeRemainingTime = remember { mutableStateOf(breakTimeInMinutes * 60) }
                                    var breakTimeElapsedTime by remember { mutableStateOf(0L) }
                                    val breakTimer = remember {
                                        CoroutineTimer(intervalMillis = 1000) { timeLeft ->
                                            breakTimeRemainingTime.value = (timeLeft / 1000).toInt()
                                            breakTimeElapsedTime = (breakTimeInMinutes * 60) - (timeLeft / 1000)
                                        }
                                    }
                                    LaunchedEffect(breakTimeInMinutes) {
                                        breakTimer.start(breakTimeInMinutes * 60 * 1000L)
                                    }
                                    val breakTimeMinutes = breakTimeRemainingTime.value / 60
                                    val breakTimeSeconds = breakTimeRemainingTime.value % 60
                                    Popup(alignment = Alignment.BottomCenter) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .offset(y = (-10).dp)
                                                .padding(bottom = 35.dp)
                                                .width(300.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.popup),
                                                contentDescription = null,
                                            )
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "BreakTime",
                                                    fontSize = 30.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Spacer(modifier = Modifier.height(30.dp))
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Spacer(modifier = Modifier.width(65.dp))
                                                    Text(
                                                        text = breakTimeMinutes.toString().padStart(2, '0'),
                                                        fontSize = 60.sp,
                                                        fontWeight = FontWeight.Light,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.weight(1f),
                                                        textAlign = TextAlign.End
                                                    )
                                                    Text(
                                                        text = ":",
                                                        fontSize = 60.sp,
                                                        fontWeight = FontWeight.Light,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.weight(0.5f),
                                                        textAlign = TextAlign.Center
                                                    )
                                                    Text(
                                                        text = breakTimeSeconds.toString().padStart(2, '0'),
                                                        fontSize = 60.sp,
                                                        fontWeight = FontWeight.Light,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.weight(1f),
                                                        textAlign = TextAlign.Start
                                                    )
                                                    Spacer(modifier = Modifier.width(65.dp))
                                                }
                                                Spacer(modifier = Modifier.height(30.dp))
                                                OutlinedButton(
                                                    text = "Done",
                                                    textSize = 24,
                                                    buttonWidth = 100,
                                                    buttonHeight = 50,
                                                    color = Color.White,
                                                    onClick = {
                                                        breakTimer.stop()
                                                        totalTimeInSeconds += breakTimeElapsedTime
                                                        showPopup.value = false
                                                        timer.resume()
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                OutlinedButton(
                                    text = "Break",
                                    buttonWidth = 210,
                                    color = Color(0xffDFB463),
                                    onClick = {
                                        timer.pause()
                                        showPopup.value = true
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            LongPressButtonWithProgress(
                                text = "Finish",
                                buttonWidth = 210,
                                color = Color(0xffDF7163),
                                onClick = {
                                    timer.stop()
                                    totalTimeInSeconds += elapsedTime
                                    coroutineScope.launch {
                                        val updatedStudiedTime = totalTimeInSeconds.toInt()
                                        viewModel.updateStudyRecord(
                                            studyRecordId = studyRecordId,
                                            studiedTime = updatedStudiedTime
                                        )
                                    }
                                    navigateToHome()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

/*@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TimerScreen(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    timerType: String = "pomodoroTimer", // "startStudy" or "pomodoroTimer"
    subjectId: Int?,
    studyRecordId: Int,
    timerValueInMinutes: Int = 10,
    breakTimeInMinutes: Int = 5,
    navigateToHome: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    // --- 共通：科目情報取得 ---
    val subjectItem = remember(subjectId) { mutableStateOf<Subjects?>(null) }
    LaunchedEffect(subjectId) {
        if (subjectId != null) {
            val result = viewModel.getSubjectsById(subjectId) // suspend関数を呼び出し
            subjectItem.value = result
        }
    }

    // --- 共通：日付・曜日表示 ---
    val date = LocalDate.now()
    val dayOfWeek = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

    when (timerType) {
        "startStudy" -> {
            // ★ startStudy モード（既存実装）
            // 勉強タイマー（ユーザー操作でブレイク開始可能）
            var totalTimeInSeconds by remember { mutableLongStateOf(0L) }
            var elapsedTime by remember { mutableLongStateOf(0L) }
            // 秒数（初期値：指定分×60）
            val remainingTime = remember { mutableIntStateOf(timerValueInMinutes * 60) }
            val timer = remember {
                CoroutineTimer(intervalMillis = 1000) { timeLeft ->
                    remainingTime.value = (timeLeft / 1000).toInt()
                    elapsedTime = (timerValueInMinutes * 60) - (timeLeft / 1000)
                }
            }
            LaunchedEffect(timerValueInMinutes) {
                timer.start(timerValueInMinutes * 60 * 1000L)
            }
            val minutes = remainingTime.value / 60
            val seconds = remainingTime.value % 60
            val showPopup = remember { mutableStateOf(false) }

            // subjectItemが取得できた場合のUI表示
            subjectItem.value?.let { subject ->
                StudyAppTheme {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(70.dp))
                            Text(
                                text = "$date, $dayOfWeek",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Image(
                                painter = painterResource(id = subject.imageId),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color(subject.color)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(80.dp)
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.width(55.dp))
                                Text(
                                    text = minutes.toString().padStart(2, '0'),
                                    fontSize = 100.sp,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                                Text(
                                    text = ":",
                                    fontSize = 100.sp,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(0.5f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = seconds.toString().padStart(2, '0'),
                                    fontSize = 100.sp,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start
                                )
                                Spacer(modifier = Modifier.width(55.dp))
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            LeftTimeBar(
                                originalTime = timerValueInMinutes * 60 * 1000L,
                                leftTime = remainingTime.value * 1000L
                            )
                            Spacer(modifier = Modifier.height(90.dp))
                            Box {
                                // ポップアップ（ブレイクタイマー）表示時のUI
                                if (showPopup.value) {
                                    val breakTimeRemainingTime = remember { mutableStateOf(breakTimeInMinutes * 60) }
                                    var breakTimeElapsedTime by remember { mutableStateOf(0L) }
                                    val breakTimer = remember {
                                        CoroutineTimer(intervalMillis = 1000) { timeLeft ->
                                            breakTimeRemainingTime.value = (timeLeft / 1000).toInt()
                                            breakTimeElapsedTime = (breakTimeInMinutes * 60) - (timeLeft / 1000)
                                        }
                                    }
                                    LaunchedEffect(breakTimeInMinutes) {
                                        breakTimer.start(breakTimeInMinutes * 60 * 1000L)
                                    }
                                    val breakTimeMinutes = breakTimeRemainingTime.value / 60
                                    val breakTimeSeconds = breakTimeRemainingTime.value % 60
                                    Popup(alignment = Alignment.BottomCenter) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .offset(y = (-10).dp)
                                                .padding(bottom = 35.dp)
                                                .width(300.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.popup),
                                                contentDescription = null,
                                            )
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "BreakTime",
                                                    fontSize = 30.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Spacer(modifier = Modifier.height(30.dp))
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Spacer(modifier = Modifier.width(65.dp))
                                                    Text(
                                                        text = breakTimeMinutes.toString().padStart(2, '0'),
                                                        fontSize = 60.sp,
                                                        fontWeight = FontWeight.Light,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.weight(1f),
                                                        textAlign = TextAlign.End
                                                    )
                                                    Text(
                                                        text = ":",
                                                        fontSize = 60.sp,
                                                        fontWeight = FontWeight.Light,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.weight(0.5f),
                                                        textAlign = TextAlign.Center
                                                    )
                                                    Text(
                                                        text = breakTimeSeconds.toString().padStart(2, '0'),
                                                        fontSize = 60.sp,
                                                        fontWeight = FontWeight.Light,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.weight(1f),
                                                        textAlign = TextAlign.Start
                                                    )
                                                    Spacer(modifier = Modifier.width(65.dp))
                                                }
                                                Spacer(modifier = Modifier.height(30.dp))
                                                OutlinedButton(
                                                    text = "Done",
                                                    textSize = 24,
                                                    buttonWidth = 100,
                                                    buttonHeight = 50,
                                                    color = Color.White,
                                                    onClick = {
                                                        breakTimer.stop()
                                                        totalTimeInSeconds += breakTimeElapsedTime
                                                        showPopup.value = false
                                                        timer.resume()
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                // ブレイクボタン（startstudyモードでは任意操作でブレイク開始）
                                OutlinedButton(
                                    text = "Break",
                                    buttonWidth = 210,
                                    color = Color(0xffDFB463),
                                    onClick = {
                                        timer.pause()
                                        showPopup.value = true
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            LongPressButtonWithProgress(
                                text = "Finish",
                                buttonWidth = 210,
                                color = Color(0xffDF7163),
                                onClick = {
                                    timer.stop()
                                    totalTimeInSeconds += elapsedTime
                                    coroutineScope.launch {
                                        val updatedStudiedTime = totalTimeInSeconds.toInt()
                                        viewModel.updateStudyRecord(
                                            studyRecordId = studyRecordId,
                                            studiedTime = updatedStudiedTime
                                        )
                                    }
                                    // ※ 必要に応じて coroutineScope を用いてデータ更新処理を実施
                                    navigateToHome()
                                }
                            )
                        }
                    }
                }
            }
        }

        "pomodoroTimer" -> {
            // ★ pomodoroTimer モード（ポモドーロテクニック）
            // ・初回は25分の勉強タイマーを自動起動
            // ・25分経過で自動的にポップアップ表示と5分のブレイクタイマー開始
            // ・5分経過で自動的に25分タイマーを再開する（以降ループ）
            // ・ブレイク開始ボタンは表示しない
            // ・1秒ごとに総経過時間（pomodoroTotalTime）を更新・表示する
            var pomodoroTotalTime by remember { mutableLongStateOf(0L) }
            var isBreakTime by remember { mutableStateOf(false) }
            var studyRemainingTime by remember { mutableIntStateOf(25 * 60) }   // 25分 → 1500秒
            var breakRemainingTime by remember { mutableIntStateOf(5 * 60) }      // 5分  → 300秒

            // タイマー（フェーズに応じて25分または5分をカウントダウン）
            val timer = remember {
                CoroutineTimer(intervalMillis = 1000) { timeLeft ->
                    if (!isBreakTime) {
                        studyRemainingTime = (timeLeft / 1000).toInt()
                    } else {
                        breakRemainingTime = (timeLeft / 1000).toInt()
                    }
                    pomodoroTotalTime += 1000 // 毎秒加算
                }
            }

            // フェーズ変更時に該当タイマーを開始する（isBreakTimeが false→study, true→break）
            LaunchedEffect(isBreakTime) {
                timer.stop()
                if (!isBreakTime) {
                    studyRemainingTime = 25 * 60
                    timer.start(25 * 60 * 1000L)
                } else {
                    breakRemainingTime = 5 * 60
                    timer.start(5 * 60 * 1000L)
                }
            }

            // タイマーの残り時間が0になったら自動的にフェーズを切替える
            LaunchedEffect(studyRemainingTime, breakRemainingTime, isBreakTime) {
                if (!isBreakTime && studyRemainingTime <= 0) {
                    timer.stop()
                    isBreakTime = true
                }
                if (isBreakTime && breakRemainingTime <= 0) {
                    timer.stop()
                    isBreakTime = false
                }
            }

            subjectItem.value?.let { subject ->
                StudyAppTheme {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(70.dp))
                            Text(
                                text = "$date, $dayOfWeek",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Spacer(modifier = Modifier.height(40.dp))
                            // 現在フェーズに応じた残り時間の表示
                            val remainingTime = if (!isBreakTime) studyRemainingTime else breakRemainingTime
                            val minutes = remainingTime / 60
                            val seconds = remainingTime % 60
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.width(55.dp))
                                Text(
                                    text = minutes.toString().padStart(2, '0'),
                                    fontSize = if (!isBreakTime) 100.sp else 60.sp,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                                Text(
                                    text = ":",
                                    fontSize = if (!isBreakTime) 100.sp else 60.sp,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(0.5f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = seconds.toString().padStart(2, '0'),
                                    fontSize = if (!isBreakTime) 100.sp else 60.sp,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start
                                )
                                Spacer(modifier = Modifier.width(55.dp))
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            // フェーズに応じたプログレスバー
                            if (!isBreakTime) {
                                LeftTimeBar(
                                    originalTime = 25 * 60 * 1000L,
                                    leftTime = (studyRemainingTime * 1000).toLong()
                                )
                            } else {
                                LeftTimeBar(
                                    originalTime = 5 * 60 * 1000L,
                                    leftTime = (breakRemainingTime * 1000).toLong()
                                )
                            }
                            Spacer(modifier = Modifier.height(90.dp))
                            // 総経過時間の表示（単位：秒）
                            Text(
                                text = "Total elapsed time: $pomodoroTotalTime sec",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            // 終了ボタン（どちらのフェーズ中でも手動で終了可能）
                            OutlinedButton(
                                text = "Finish",
                                buttonWidth = 210,
                                color = Color(0xffDF7163),
                                onClick = {
                                    timer.stop()
                                    navigateToHome()
                                }
                            )
                        }
                        // ブレイクフェーズ時は自動的にポップアップ表示する
                        if (isBreakTime) {
                            Popup(alignment = Alignment.BottomCenter) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .offset(y = (-10).dp)
                                        .padding(bottom = 35.dp)
                                        .width(300.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.popup),
                                        contentDescription = null,
                                    )
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Break Time",
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(30.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Spacer(modifier = Modifier.width(65.dp))
                                            Text(
                                                text = (breakRemainingTime / 60).toString().padStart(2, '0'),
                                                fontSize = 60.sp,
                                                fontWeight = FontWeight.Light,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.weight(1f),
                                                textAlign = TextAlign.End
                                            )
                                            Text(
                                                text = ":",
                                                fontSize = 60.sp,
                                                fontWeight = FontWeight.Light,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.weight(0.5f),
                                                textAlign = TextAlign.Center
                                            )
                                            Text(
                                                text = (breakRemainingTime % 60).toString().padStart(2, '0'),
                                                fontSize = 60.sp,
                                                fontWeight = FontWeight.Light,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.weight(1f),
                                                textAlign = TextAlign.Start
                                            )
                                            Spacer(modifier = Modifier.width(65.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}*/

/*@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TimerScreen(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
    timerType: String,
    subjectId: Int?,
    studyRecordId: Int,
    timerValueInMinutes: Int = 10,
    breakTimeInMinutes: Int = 5,
    navigateToHome: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    // subjectIdがnullでないか確認
    val subjectItem = remember(subjectId) {
        mutableStateOf<Subjects?>(null) // 初期値をnullにする
    }

    // CoroutineScopeで非同期処理を実行
    LaunchedEffect(subjectId) {
        if (subjectId != null) {
            val result = viewModel.getSubjectsById(subjectId) // suspend関数を非同期に呼び出し
            subjectItem.value = result
        }
    }

    val date = LocalDate.now()
    val dayOfWeek = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

    // 秒数で保持
    var totalTimeInSeconds by remember { mutableStateOf(0L) }

    var elapsedTime by remember { mutableStateOf(0L) }

    // タイマーの残り時間（秒単位で保持）
    val remainingTime = remember { mutableStateOf(timerValueInMinutes * 60) }

    val timer = remember {
        CoroutineTimer(intervalMillis = 1000) { timeLeft ->
            remainingTime.value = (timeLeft / 1000).toInt()
            elapsedTime = (timerValueInMinutes * 60) - (timeLeft / 1000)
        }
    }

    LaunchedEffect(timerValueInMinutes) {
        timer.start(timerValueInMinutes * 60 * 1000L)
    }

    val minutes = remainingTime.value / 60
    val seconds = remainingTime.value % 60

    var showPopup = remember { mutableStateOf(false) }

    // subjectItem.valueがnullでないか確認してUIを更新
    subjectItem.value?.let { subjectItem ->

        StudyAppTheme {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(70.dp))
                    Text(
                        text = "$date, $dayOfWeek",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Image(
                        painter = painterResource(id = subjectItem.imageId),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color(subjectItem.color)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(55.dp))
                        Text(
                            text = minutes.toString().padStart(2, '0'),
                            fontSize = 100.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End // 右寄せ
                        )
                        Text(
                            text = ":",
                            fontSize = 100.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(0.5f), // 中央に配置
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = seconds.toString().padStart(2, '0'), // 1桁の秒数を0埋め
                            fontSize = 100.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start // 左寄せ
                        )
                        Spacer(modifier = Modifier.width(55.dp))
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    LeftTimeBar(timerValueInMinutes * 60 * 1000L, remainingTime.value * 1000L)
                    Spacer(modifier = Modifier.height(90.dp))
                    Box {
                        if (showPopup.value) {
                            val breakTimeRemainingTime = remember { mutableStateOf(breakTimeInMinutes * 60) }

                            var breakTimeElapsedTime by remember { mutableStateOf(0L) }

                            val breakTimer = remember {
                                CoroutineTimer(intervalMillis = 1000) { timeLeft ->
                                    breakTimeRemainingTime.value = (timeLeft / 1000).toInt()
                                    breakTimeElapsedTime = (breakTimeInMinutes * 60) - (timeLeft / 1000)
                                }
                            }

                            LaunchedEffect(breakTimeInMinutes) {
                                breakTimer.start(breakTimeInMinutes * 60 * 1000L)
                            }

                            val breakTimeMinutes = breakTimeRemainingTime.value / 60
                            val breakTimeSeconds = breakTimeRemainingTime.value % 60

                            Popup(
                                alignment = Alignment.BottomCenter
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .offset(y = (-10).dp)
                                        .padding(bottom = 35.dp)
                                        .width(300.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.popup),
                                        contentDescription = null,
                                    )
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                    ) {
                                        Text(
                                            text = "BreakTime",
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(30.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Spacer(modifier = Modifier.width(65.dp))
                                            Text(
                                                text = breakTimeMinutes.toString().padStart(2, '0'),
                                                fontSize = 60.sp,
                                                fontWeight = FontWeight.Light,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.weight(1f),
                                                textAlign = TextAlign.End // 右寄せ
                                            )
                                            Text(
                                                text = ":",
                                                fontSize = 60.sp,
                                                fontWeight = FontWeight.Light,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.weight(0.5f), // 中央に配置
                                                textAlign = TextAlign.Center
                                            )
                                            Text(
                                                text = breakTimeSeconds.toString().padStart(2, '0'), // 1桁の秒数を0埋め
                                                fontSize = 60.sp,
                                                fontWeight = FontWeight.Light,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.weight(1f),
                                                textAlign = TextAlign.Start // 左寄せ
                                            )
                                            Spacer(modifier = Modifier.width(65.dp))
                                        }
                                        Spacer(modifier = Modifier.height(30.dp))
                                        OutlinedButton(
                                            text = "Done",
                                            textSize = 24,
                                            buttonWidth = 100,
                                            buttonHeight = 50,
                                            color = Color.White,
                                            onClick = {
                                                breakTimer.stop()
                                                totalTimeInSeconds += breakTimeElapsedTime
                                                showPopup.value = false
                                                timer.resume()
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        OutlinedButton(
                            text = "Break",
                            buttonWidth = 210,
                            color = Color(0xffDFB463),
                            onClick = {
                                timer.pause()
                                showPopup.value = true
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    LongPressButtonWithProgress(
                        text = "Finish",
                        buttonWidth = 210,
                        color = Color(0xffDF7163),
                        onClick = {
                            timer.stop()
                            totalTimeInSeconds += elapsedTime
                            // ここでは rememberCoroutineScope() を利用してコルーチンを起動
                            coroutineScope.launch {
                                val updatedStudiedTime = totalTimeInSeconds.toInt()
                                viewModel.updateStudyRecord(
                                    studyRecordId = studyRecordId,
                                    studiedTime = updatedStudiedTime
                                )
                            }
                            navigateToHome()
                        }
                    )
                }
            }
        }

    }
}*/

@Composable
fun LeftTimeBar(
    originalTime: Long,
    leftTime: Long,
) {
    val progress = (leftTime.toFloat() / originalTime.toFloat()).coerceIn(0f, 1f) // 0～1に制限
    val targetWidth = (progress * 300).dp

    val animatedWidth by animateDpAsState(
        targetValue = targetWidth,
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(16.dp)
            .clip(RoundedCornerShape(100))
            .background(Color(0xff2E2E2E))
    ) {
        Box(
            modifier = Modifier
                .width(animatedWidth)
                .height(16.dp)
                .clip(RoundedCornerShape(100))
                .background(Color(0xff8AB4F8))
        )
    }
}

@Preview
@Composable
private fun LeftTimeBarPreview() {
    Column {
        Text(text = "100% (Full)")
        LeftTimeBar(30 * 60 * 1000, 30 * 60 * 1000) // 100% (Full)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "50%")
        LeftTimeBar(30 * 60 * 1000, 15 * 60 * 1000) // 50%

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "10% (Almost Empty)")
        LeftTimeBar(30 * 60 * 1000, 3 * 60 * 1000) // 10%

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "0% (Empty)")
        LeftTimeBar(30 * 60 * 1000, 0) // 0% (Empty)
    }
}

@Preview
@Composable
private fun TimerScreenPreview() {
    val mockRepository = object : AppRepository {
        override fun getAllVocabularies(): Flow<List<VocabularyEntity>> {
            TODO("Not yet implemented")
        }

        override fun getQuestionWithAnswers(vocabularyId: Int): Flow<List<QuestionWithAnswers>> {
            TODO("Not yet implemented")
        }

        override suspend fun insertVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun deleteVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun deleteAllRelatedData(vocabularyId: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun updateVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun insertQuestion(question: QuestionEntity): Int {
            TODO("Provide the return value")
        }

        override suspend fun updateQuestion(question: QuestionEntity) {}
        override suspend fun updateIsLiked(questionId: Int, isLiked: Boolean) {
            TODO("Not yet implemented")
        }

        override suspend fun insertPairAnswer(answer: PairAnswerEntity) {}
        override suspend fun updatePairAnswer(answer: PairAnswerEntity) {}
        override suspend fun insertCompletionAnswer(answer: CompletionAnswerEntity) {}
        override suspend fun updateCompletionAnswer(answer: CompletionAnswerEntity) {}
        override suspend fun insertChoiceAnswer(answer: ChoiceAnswerEntity) {}
        override suspend fun updateChoiceAnswer(answer: ChoiceAnswerEntity) {}
        override suspend fun updateUncorrectedNumber(questionId: Int, uncorrectedNumber: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun updateCorrectedNumber(questionId: Int, correctNumber: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun getSelectedSubjects(): Flow<List<Subjects>> {
            return MutableStateFlow(
                listOf(
                    Subjects(1, "math", R.drawable.function, 0xff8AB4F8, 0xff394557),
                    Subjects(2, "english", R.drawable.earth, 0xff9C92F7, 0xff313142),
                    Subjects(3, "japanese", R.drawable.earth, 0xff9C92F7, 0xff313142),
                    Subjects(4, "science", R.drawable.earth, 0xff9C92F7, 0xff313142),
                )
            )
        }

        override suspend fun getSubjectById(subjectId: Int): Subjects {
            return Subjects(1, "math", R.drawable.function, 0xff8AB4F8, 0xff394557)
        }

        override suspend fun getStudyTimes(): Flow<List<StudyTimes>> {
            TODO("Not yet implemented")
        }

        override suspend fun insertStudyTime(studyTime: StudyTimes) {
            TODO("Not yet implemented")
        }

        override suspend fun getAllStudyRecords(): Flow<List<StudyRecords>> {
            TODO("Not yet implemented")
        }

        override suspend fun getStudyRecordById(studyRecordId: Int): StudyRecords {
            TODO("Not yet implemented")
        }

        override suspend fun insertStudyRecord(studyRecord: StudyRecords): Int {
            TODO("Not yet implemented")
        }

        override suspend fun updateStudyRecord(studyRecord: StudyRecords) {
            TODO("Not yet implemented")
        }
    }

    val viewModel = StartStudyViewModel(mockRepository)

    TimerScreen(
        viewModel = viewModel,
        timerType = "StartStudy",
        subjectId = 1,
        studyRecordId = 1
    )
}