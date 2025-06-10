package com.example.idosodev.ui.MedicineRegister

import com.example.idosodev.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idosodev.ui.components.FieldTextOutlined
import com.example.idosodev.ui.config.OutlinedInputConfig
import com.example.idosodev.ui.themes.black
import com.example.idosodev.ui.themes.green_background

@Composable
fun RegisterMedicineScreen() {
    var medicineName by remember { mutableStateOf("") }
    var via by remember { mutableStateOf("") }
    var dose by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var period by remember { mutableStateOf("") }

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
                .background(
                    color = green_background,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(20.dp)
                .shadow(4.dp, shape = RoundedCornerShape(12.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pill),
                contentDescription = "Ícone de medicamento",
                modifier = Modifier
                    .fillMaxSize(),
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

        Row {
            FieldTextOutlined(
                value = via,
                onChange = { via = it},
                config = OutlinedInputConfig(
                    label = "Via",
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                )
            )

            FieldTextOutlined(
                value = dose,
                onChange = { dose = it},
                config = OutlinedInputConfig(
                    label = "Dose",
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun RegisterMedicineScreenPreview() {
    MaterialTheme {
        RegisterMedicineScreen()
    }
}
