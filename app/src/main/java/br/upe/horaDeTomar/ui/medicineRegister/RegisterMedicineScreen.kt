package br.upe.horaDeTomar.ui.medicineRegister

import com.upe.horaDeTomar.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upe.horaDeTomar.ui.components.FieldTextOutlined
import com.upe.horaDeTomar.ui.components.OutlinedTimePickerDialog
import com.upe.horaDeTomar.ui.components.RegisterButton
import com.upe.horaDeTomar.ui.components.SelectPhotoButton
import com.upe.horaDeTomar.ui.components.TakePhotoButton
import com.upe.horaDeTomar.ui.config.OutlinedInputConfig
import com.upe.horaDeTomar.ui.themes.black
import com.upe.horaDeTomar.ui.themes.green_secondary

@Composable
fun RegisterMedicineScreen() {
    var medicineName by remember { mutableStateOf("") }
    var via by remember { mutableStateOf("") }
    var dose by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var period by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
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
            onChange = { medicineName = it},
            config = OutlinedInputConfig(
                label = "Nome do Medicamento",
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            )
        )

        Row (modifier = Modifier.fillMaxWidth()) {
            FieldTextOutlined(
                modifier = Modifier.weight(1f),
                value = via,
                onChange = { via = it},
                contentPadding = PaddingValues(start = 32.dp, end = 0.dp, top = 0.dp, bottom = 16.dp),
                config = OutlinedInputConfig(
                    label = "Via",
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                )
            )

            Spacer(modifier = Modifier.width(4.dp))

            FieldTextOutlined(
                modifier = Modifier.weight(1f),
                value = dose,
                onChange = { dose = it},
                contentPadding = PaddingValues(start = 0.dp, end = 32.dp, top = 0.dp, bottom = 16.dp),
                config = OutlinedInputConfig(
                    label = "Dose",
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Number
                )
            )
        }

        OutlinedTextField(
            value = time,
            onValueChange = {},
            readOnly = true,
            label = { Text("Horário") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp, top = 0.dp)
                .clickable { showTimePicker = true },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )

        if ( showTimePicker ) {
            OutlinedTimePickerDialog(
                onDismissRequest = { showTimePicker = false },
                onTimeSelected = { hour, minute ->
                    time = String.format("%02d:%02d", hour, minute)
                    showTimePicker = false
                },
                initialHour = 10,
                initialMinute = 0,
                title = "Selecione o horário"
            )
        }

        FieldTextOutlined(
            value = period,
            onChange = { period = it},
            contentPadding = PaddingValues(start = 32.dp, end = 32.dp, top = 0.dp, bottom = 16.dp),
            config = OutlinedInputConfig(
                label = "Período",
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SelectPhotoButton(
                onClick = {

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

        RegisterButton(
            onClick = { println("Remédio cadastrado!")},
            label = "Cadastrar Medicamento",

        )
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun RegisterMedicineScreenPreview() {
    MaterialTheme {
        RegisterMedicineScreen()
    }
}
