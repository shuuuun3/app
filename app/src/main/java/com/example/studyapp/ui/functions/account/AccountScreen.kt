package com.example.studyapp.ui.functions.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.studyapp.data.StudyRecords
import com.example.studyapp.data.StudyTimes
import com.example.studyapp.data.Subjects
import com.example.studyapp.data.VocabularyEntity
import com.example.studyapp.ui.navigation.NavigationDestination
import com.example.studyapp.ui.theme.StudyAppTheme
import kotlinx.coroutines.flow.Flow

object AccountDestinations : NavigationDestination {
    override val route = "account"
    override val titleRes = R.string.entry_account
}

@Composable
fun AccountScreen() {
    AccountBody()
}

@Composable
fun AccountBody(
    viewModel: StartStudyViewModel = viewModel(factory = StartStudyViewModelProvider.Factory),
) {
    val totalStudyTime by viewModel.totalStudyTime.collectAsState()
    val totalHours = totalStudyTime / 3600
    val totalMinutes = (totalStudyTime % 3600) / 60

    StudyAppTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(370.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(20))
                    .background(Color(0xff1f1f1f))
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.account_circle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp)
                        .clickable {  }
                )
                Text(
                    text = "Shunsuke Miyazaki",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.tune),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color(0xffCFCFCF)),
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {  }
                )
                Spacer(modifier = Modifier.width(20.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(370.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(20))
                    .background(Color(0xff7C4848))
            ) {
                Spacer(modifier = Modifier.width(30.dp))
                Text(text = "Total", fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${totalHours}h ${totalMinutes}m",
                    fontSize = 35.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
private fun AccountScreenPreview() {
    val mockRepository = object : AppRepository {
        override fun getQuestionWithAnswers(vocabularyId: Int): Flow<List<QuestionWithAnswers>> {
            TODO("Not yet implemented")
        }

        override fun getAllVocabularies(): Flow<List<VocabularyEntity>> {
            TODO("Not yet implemented")
        }

        override suspend fun insertVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun deleteVocabulary(vocabulary: VocabularyEntity) {}
        override suspend fun deleteAllRelatedData(vocabularyId: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteQuestionAndAnswers(questionId: Int) {
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
            TODO("Not yet implemented")
        }

        override suspend fun getSubjectById(subjectId: Int): Subjects {
            TODO("Not yet implemented")
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

    AccountBody(viewModel = viewModel)
}