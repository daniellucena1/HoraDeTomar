package br.upe.horaDeTomar.ui.medications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.upe.horaDeTomar.ui.components.AddHomePageCard
import br.upe.horaDeTomar.ui.components.HeaderSection
import br.upe.horaDeTomar.ui.components.MedicineHomePageCard
import br.upe.horaDeTomar.ui.components.OptionsCard
import br.upe.horaDeTomar.ui.components.UserCard
import br.upe.horaDeTomar.ui.themes.black

@Composable
fun MedicationsScreen(modifier: Modifier = Modifier) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state)
                .padding(bottom = 8.dp),
        ) {
            Text(
                text = "Medicamentos",
                fontSize = 16.sp,
                color = black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            )

            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
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
            }
        }

        OptionsCard(
            iconName = "ic_plus",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { /* TODO: Implement add medication action */ },
            label = "Adicionar Remédio"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun MedicationsScreenPreview() {
    MedicationsScreen()
}