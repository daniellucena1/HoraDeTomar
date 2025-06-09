package com.example.idosodev.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idosodev.R
import com.example.idosodev.presentation.ui.theme.IdosoDevTheme // Seu tema
import com.example.idosodev.presentation.ui.theme.GreenPrimary // Suas cores
import com.example.idosodev.presentation.ui.theme.GreenSecondary
import com.example.idosodev.presentation.ui.theme.Black
import com.example.idosodev.presentation.components.TimePicker // Importe seu TimePicker Composable
import com.example.idosodev.presentation.features.medicineregister.MedicineRegisterUiState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineRegisterScreen(
    // Estado da UI recebido do ViewModel
    uiState: MedicineRegisterUiState,
    // Callbacks para atualizar o estado no ViewModel
    onNameChange: (String) -> Unit,
    onViaChange: (String) -> Unit,
    onDosageChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onPeriodChange: (String) -> Unit,
    // Callbacks para ações
    onTimeFieldClick: () -> Unit,
    onTimePickerDismissed: () -> Unit,
    onSelectPhotoClick: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onRegisterMedicineClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Ícone do remédio
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .background(GreenSecondary, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pill), // Assegure-se de ter este drawable
                    contentDescription = "Ícone de medicamento",
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Campo: Nome do medicamento
            OutlinedTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                label = { Text("Nome do medicamento") },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Campo: Via
                OutlinedTextField(
                    value = uiState.via,
                    onValueChange = onViaChange,
                    label = { Text("Via") },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Campo: Dose
                OutlinedTextField(
                    value = uiState.dosage,
                    onValueChange = onDosageChange,
                    label = { Text("Dose") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Horário (com seletor de tempo)
            OutlinedTextField(
                value = uiState.time,
                onValueChange = { /* Não há onValueChange direto aqui, pois a mudança vem do TimePicker */ },
                label = { Text("Horário") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTimeFieldClick() }, // Clique no campo para abrir o seletor
                readOnly = true // Torna o campo não editável via teclado
            )

            // NOVO: Invoca o TimePicker Composable condicionalmente
            if (uiState.showTimePicker) {
                val calendar = Calendar.getInstance()
                TimePicker(
                    initialHour = calendar.get(Calendar.HOUR_OF_DAY),
                    initialMinute = calendar.get(Calendar.MINUTE),
                    onTimeSelected = onTimeChange,
                    showDialog = uiState.showTimePicker,
                    onDismissRequest = onTimePickerDismissed
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Período
            OutlinedTextField(
                value = uiState.period,
                onValueChange = onPeriodChange,
                label = { Text("Período") },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botões de Selecionar Foto e Tirar Foto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onSelectPhotoClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                        .padding(end = 4.dp)
                        .shadow(6.dp, RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenSecondary),
                    shape = RoundedCornerShape(12.dp),
                    border = _root_ide_package_.androidx.compose.foundation.BorderStroke(1.dp, GreenPrimary),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_gallery), // Assegure-se de ter este drawable
                        contentDescription = null,
                        tint = Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onTakePhotoClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                        .padding(start = 4.dp)
                        .shadow(6.dp, RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenSecondary),
                    shape = RoundedCornerShape(12.dp),
                    border = _root_ide_package_.androidx.compose.foundation.BorderStroke(1.dp, GreenPrimary),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cam), // Assegure-se de ter este drawable
                        contentDescription = null,
                        tint = Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botão Cadastrar Medicamento
            Button(
                onClick = onRegisterMedicineClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                shape = RoundedCornerShape(15.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(
                    text = "Cadastrar medicamento",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicineRegisterScreenPreview() {
    IdosoDevTheme {
        MedicineRegisterScreen(
            uiState = MedicineRegisterUiState(
                name = "Paracetamol",
                via = "Oral",
                dosage = "500mg",
                time = "08:00",
                period = "8/8 horas"
            ),
            onNameChange = {},
            onViaChange = {},
            onDosageChange = {},
            onTimeChange = {},
            onPeriodChange = {},
            onTimeFieldClick = {},
            onTimePickerDismissed = {},
            onSelectPhotoClick = {},
            onTakePhotoClick = {},
            onRegisterMedicineClick = {}
        )
    }
}