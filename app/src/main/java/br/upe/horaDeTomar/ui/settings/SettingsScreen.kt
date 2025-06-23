package br.upe.horaDeTomar.ui.settings

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.upe.horaDeTomar.ui.components.HeaderSection
import br.upe.horaDeTomar.ui.components.OptionsCard
import br.upe.horaDeTomar.ui.components.ReminderCard
import br.upe.horaDeTomar.ui.components.SettingsCard
import br.upe.horaDeTomar.ui.themes.black

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    var isChecked by remember { mutableStateOf(false) }

    fun onReminderToggle(newValue: Boolean) {
        isChecked = newValue
    }

    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state)
                .padding(bottom = 8.dp),
        ){

            Text(
                text = "Próximos Medicamentos",
                fontSize = 16.sp,
                color = black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            )

            Column (
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp )
            ){
                SettingsCard(
                    isChecked = isChecked,
                    onClick = ::onReminderToggle
                )
                Spacer(modifier = Modifier.height(10.dp))
                SettingsCard(
                    isChecked = isChecked,
                    onClick = ::onReminderToggle
                )
                Spacer(modifier = Modifier.height(10.dp))
                SettingsCard(
                    isChecked = isChecked,
                    onClick = ::onReminderToggle
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}