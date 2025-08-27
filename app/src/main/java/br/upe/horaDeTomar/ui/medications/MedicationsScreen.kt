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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.upe.horaDeTomar.data.entities.Medication
import br.upe.horaDeTomar.ui.components.AddHomePageCard
import br.upe.horaDeTomar.ui.components.HeaderSection
import br.upe.horaDeTomar.ui.components.MedicineHomePageCard
import br.upe.horaDeTomar.ui.components.OptionsCard
import br.upe.horaDeTomar.ui.components.UserCard
import br.upe.horaDeTomar.ui.themes.black
import kotlin.math.min

@Composable
fun MedicationsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MedicationsViewModel = hiltViewModel()
) {
    // States e ViewModel
    val medications by viewModel.medications.collectAsState()
    val alarmStateList by viewModel.alarmListState.collectAsState()

    // State de scroll
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
                medications.forEach {
                    medication ->
                    var hour = ""
                    var minute = ""
                    alarmStateList.forEach {
                        alarm ->
                        if (alarm.medicationId == medication.id) {
                            hour = alarm.hour
                            minute = alarm.minute
                        }
                    }
                    MedicineHomePageCard(
                        medicineName = medication.name,
                        dose = medication.dose,
                        time = "${hour}:${minute}"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }
        }

        OptionsCard(
            iconName = "ic_plus",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp),
            onClick = { navController.navigate("registerMedication") },
            label = "Adicionar Remédio"
        )
    }
}
