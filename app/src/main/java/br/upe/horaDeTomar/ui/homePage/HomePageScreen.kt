package br.upe.horaDeTomar.ui.homePage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.upe.horaDeTomar.ui.components.AddHomePageCard
import br.upe.horaDeTomar.ui.components.HeaderSection
import br.upe.horaDeTomar.ui.components.MedicineHomePageCard
import br.upe.horaDeTomar.ui.components.UserCard
import br.upe.horaDeTomar.ui.themes.black

@Composable
fun HomePageScreen(modifier: Modifier = Modifier) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }
    Column (
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state)
            .padding(bottom = 8.dp)
    ) {

        Text(
            text = "Usuários",
            fontSize = 16.sp,
            color = black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        Column (
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp )
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
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
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