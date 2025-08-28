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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.upe.horaDeTomar.ui.components.AddHomePageCard
import br.upe.horaDeTomar.ui.components.HeaderSection
import br.upe.horaDeTomar.ui.components.MedicineHomePageCard
import br.upe.horaDeTomar.ui.components.UserCard
import br.upe.horaDeTomar.ui.medications.MedicationsViewModel
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.users.UsersViewModel

@Composable
fun HomePageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userViewModel: UsersViewModel = hiltViewModel(),
    medicationViewModel: MedicationsViewModel = hiltViewModel()
) {
    // States de usuários e medicamentos
    val usersState by userViewModel.users.collectAsState()
    val medicationsState by medicationViewModel.medications.collectAsState()
    val alarmListState by medicationViewModel.alarmListState.collectAsState()

    // State para scroll
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
            usersState.forEach {
                users ->
                UserCard(
                    userName = users.name,
                    age = users.birthDate,
                    modifier = Modifier.padding(bottom = 10.dp),
                    imageUri = users.imageUri
                )

                Spacer(modifier = Modifier.height(10.dp))
            }


            AddHomePageCard(
                isUser = "Usuário",
                onClick = { navController.navigate("registerUser") }
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
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
        ){
            medicationsState.forEach {
                medication ->
                var hour = ""
                var minute = ""
                alarmListState.forEach { alarm ->
                    if (alarm.medicationId == medication.id) {
                        hour = alarm.hour
                        minute = alarm.minute
                    }
                }
                MedicineHomePageCard(
                    medicineName = medication.name,
                    dose = "${medication.dose} comprimido(s)",
                    time = "${hour}:${minute}",
                    imageUri = medication.imageUri
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            AddHomePageCard(
                isUser = "Medicamento",
                onClick = { navController.navigate("registerMedication") }
            )
        }
    }
}