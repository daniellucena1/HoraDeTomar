package br.upe.horaDeTomar.ui.medications

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material3.TimePicker
import br.upe.horaDeTomar.R
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.upe.horaDeTomar.data.entities.Medication
import br.upe.horaDeTomar.navigation.TopLevelsDestinations
import br.upe.horaDeTomar.ui.components.FieldTextOutlined
import br.upe.horaDeTomar.ui.components.RegisterButton
import br.upe.horaDeTomar.ui.components.SelectPhotoButton
import br.upe.horaDeTomar.ui.components.TakePhotoButton
import br.upe.horaDeTomar.ui.components.TimePickerDialog
import br.upe.horaDeTomar.ui.config.OutlinedInputConfig
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.themes.green_secondary
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterMedicineScreen(
    viewModel: MedicationsViewModel = hiltViewModel(),
    navControler: NavController,
) {

    // Variáveis de estado para os campos do formulário
    var medicineName by remember { mutableStateOf("") }
    var via by remember { mutableStateOf("") }
    var dose by remember { mutableStateOf("") }

    // Variáveis de estado para o seletor de horário
    val currentTime = Calendar.getInstance()

    // variável de estado para a imagem do remédio
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoSelectContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri}
    )

    // variáveis de erro para variáveis de estado para os campos nulas
    var isErrorOnMedicineName by remember { mutableStateOf(false) }
    var isErrorOnVia by remember { mutableStateOf(false) }
    var isErrorOnDose by remember { mutableStateOf(false) }

    // CoroutineScope para lidar com ações assíncronas, se necessário
    val coroutineScope = rememberCoroutineScope()

    var showAlarmSettingsDialog by remember { mutableStateOf(false) }

    // State para o Scroll
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(state),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp))
                .background(
                    color = green_secondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pill),
                contentDescription = "Ícone de medicamento",
                modifier = Modifier.fillMaxWidth(),
                tint = black
            )
        }

        FieldTextOutlined(
            value = medicineName,
            onChange = {
                medicineName = it
                isErrorOnMedicineName = it.isBlank()
            },
            config = OutlinedInputConfig(
                label = "Nome do Medicamento",
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            ),
            isError = isErrorOnMedicineName
        )

        Row (modifier = Modifier.fillMaxWidth()) {
            FieldTextOutlined(
                modifier = Modifier.weight(1f),
                value = via,
                onChange = {
                    via = it
                    isErrorOnVia = it.isBlank()
                },
                contentPadding = PaddingValues(start = 32.dp, end = 0.dp, top = 0.dp, bottom = 16.dp),
                config = OutlinedInputConfig(
                    label = "Via",
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                ),
                isError = isErrorOnVia
            )

            Spacer(modifier = Modifier.width(4.dp))

            FieldTextOutlined(
                modifier = Modifier.weight(1f),
                value = dose,
                onChange = {
                    dose = it
                    isErrorOnDose = it.isBlank()
                },
                contentPadding = PaddingValues(start = 0.dp, end = 32.dp, top = 0.dp, bottom = 16.dp),
                config = OutlinedInputConfig(
                    label = "Dose",
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Number
                ),
                isError = isErrorOnDose
            )
        }

        Button(
            onClick = { showAlarmSettingsDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp, top = 0.dp)
        ) {
            Text("Selecionar Horário e Dias")
        }

            if (showAlarmSettingsDialog) {
                CreateAlarmDialog(
                    alarmCreationState = viewModel.alarmCreationState,
                    alarmActions = viewModel,
                    navigateToAlarmList = {
                        showAlarmSettingsDialog = false
                    },
                    onDismissRequest = {
                        showAlarmSettingsDialog = false
                    }
                )
            }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SelectPhotoButton(
                onClick = {
                    singlePhotoSelectContract.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            )
            TakePhotoButton(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            )
        }

        if (selectedImageUri != null) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        RegisterButton(
            onClick = {
                if ( medicineName.isNotBlank() &&
                     via.isNotBlank() &&
                     dose.isNotBlank())  {
                    coroutineScope.launch {
                        val medication = Medication(
                            name = medicineName,
                            via = via,
                            dose = dose,
                            userId = 1, // Assumindo uma user ID fixa para simplificação
                            imageUri = selectedImageUri.toString()
                        )
                        viewModel.updateMedicationCreationState(medication)
                        viewModel.createMedication()
                        navControler.popBackStack()
                    }

                } else {
                    isErrorOnMedicineName = medicineName.isBlank()
                    isErrorOnVia = via.isBlank()
                    isErrorOnDose = dose.isBlank()
                }
            },
            label = "Cadastrar Medicamento",
        )
    }
}