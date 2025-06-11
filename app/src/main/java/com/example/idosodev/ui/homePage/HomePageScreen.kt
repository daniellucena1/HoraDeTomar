package com.example.idosodev.ui.homePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idosodev.ui.components.HeaderSection
import com.example.idosodev.ui.components.MedicineHomePageCard
import com.example.idosodev.ui.components.UserCard
import com.example.idosodev.ui.components.UserCardPreview
import com.example.idosodev.ui.themes.black

@Composable
fun HomePageScreen() {
    Column {
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
        }

        Text(
            text = "Remédios",
            fontSize = 16.sp,
            color = black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        Column (
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
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
        }
    }
}