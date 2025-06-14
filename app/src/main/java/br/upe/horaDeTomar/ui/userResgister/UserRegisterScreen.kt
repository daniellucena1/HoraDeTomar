package br.upe.horaDeTomar.ui.userResgister

import com.upe.horaDeTomar.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun UserRegisterScreen(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

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
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "Ícone de usuário",
                modifier = Modifier.fillMaxWidth(),
                tint = black
            )
        }

        FieldTextOutlined(
            value = userName,
            onChange = { userName = it},
            config = OutlinedInputConfig(
                label = "Nome do Medicamento",
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = birthDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Data de Nascimento") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp, top = 0.dp)
                .clickable { showDatePicker = true },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )

        if ( showDatePicker ) {
            OutlinedTimePickerDialog(
                onDismissRequest = { showDatePicker = false },
                onTimeSelected = { hour, minute ->
                    birthDate = String.format("%02d:%02d", hour, minute)
                    showDatePicker = false
                },
                initialHour = 10,
                initialMinute = 0,
                title = "Selecione a data de nascimento"
            )
        }

        FieldTextOutlined(
            value = address,
            onChange = { address = it},
            contentPadding = PaddingValues(start = 32.dp, end = 32.dp, top = 0.dp, bottom = 16.dp),
            config = OutlinedInputConfig(
                label = "Endereço",
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
            onClick = { println("Usuário cadastrado!")},
            label = "Cadastrar-se",

            )
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun RegisterMedicineScreenPreview() {
    MaterialTheme {
        UserRegisterScreen({},{})
    }
}
