package com.example.idosodev.ui.homePage

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idosodev.ui.components.AddHomePageCard
import com.example.idosodev.ui.components.HeaderSection
import com.example.idosodev.ui.components.MedicineHomePageCard
import com.example.idosodev.ui.components.UserCard
import com.example.idosodev.ui.components.UserCardPreview
import com.example.idosodev.ui.themes.black

@Composable
fun HomePageScreen(modifier: Modifier = Modifier) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }
    Column (
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state)
    ) {
        HeaderSection(
            mainIcon = "ic_user",
            hSize = 120,
            userName = "Daniel Torres"
        )

        Text(
            text = "Usuários",
            fontSize = 16.sp,
            color = black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        Column (
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ){
            UserCard(
                userName = "Daniel Torres",
                age = 21,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            UserCard(
                userName = "Vinicius Menezes",
                age = 21,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            UserCard(
                userName = "Rodrigo Belarmino",
                age = 21,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            AddHomePageCard("Usuário")
        }

        Text(
            text = "Remédios",
            fontSize = 16.sp,
            color = black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        Column (
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ){
            MedicineHomePageCard(
                medicineName = "Atorvastatina",
                dose = "1 comprimido",
                time = "18:00"
            )

            Spacer(modifier = Modifier.height(10.dp))

            MedicineHomePageCard(
                medicineName = "Valsartana",
                dose = "2 comprimido",
                time = "12:00"
            )

            Spacer(modifier = Modifier.height(10.dp))

            MedicineHomePageCard(
                medicineName = "Losartana",
                dose = "1 comprimido",
                time = "12:00"
            )

            Spacer(modifier = Modifier.height(10.dp))

            AddHomePageCard("Medicamento")
        }
    }
}