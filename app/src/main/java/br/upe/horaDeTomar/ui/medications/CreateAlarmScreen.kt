package br.upe.horaDeTomar.ui.medications

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import br.upe.horaDeTomar.data.entities.Alarm
import br.upe.horaDeTomar.ui.components.CustomChip
import br.upe.horaDeTomar.ui.components.NumberPicker
import br.upe.horaDeTomar.ui.themes.HoraDoRemedioTheme
import br.upe.horaDeTomar.ui.themes.md_theme_light_primaryContainer
import br.upe.horaDeTomar.ui.themes.white
import com.google.gson.Gson
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.ui.medications.AlarmActions

@Preview(device = Devices.PIXEL_4_XL)
@Composable
private fun CreateAlarmDialogPreview() {
    HoraDoRemedioTheme {
        CreateAlarmDialog(
            alarmCreationState = Alarm(medicationId = 1),
            alarmActions = object : AlarmActions {},
            onDismissRequest = {},
        )
    }
}

@Composable
fun CreateAlarmDialog(
    onDismissRequest: () -> Unit,
    alarmCreationState: Alarm,
    alarmActions: AlarmActions,
    navigateToAlarmList: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 4.dp,
            color = white
        ) {
            CreateAlarmScreen(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                alarmCreationState = alarmCreationState,
                alarmActions = alarmActions,
                navigateToAlarmList = navigateToAlarmList
            )
        }
    }
}

@Composable
fun CreateAlarmScreen(
    modifier: Modifier = Modifier,
    alarmCreationState: Alarm,
    alarmActions: AlarmActions,
    navigateToAlarmList: () -> Unit = {}
) {
    val cardContainerColor by animateColorAsState(targetValue = md_theme_light_primaryContainer)

    BoxWithConstraints (
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        val boxWithConstraintsScope = this
        val alarmPickerPaddingStart =
            if (boxWithConstraintsScope.maxWidth > 400.dp) {
                dimensionResource(id = R.dimen.alarm_picker_padding_start_large)
            } else {
                dimensionResource(
                    id = R.dimen.alarm_picker_padding_start_small,
                )
            }
        Column (
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            AlarmPicker(
                modifier = Modifier
                    .padding( start = alarmPickerPaddingStart, end = boxWithConstraintsScope.maxWidth/6),
                alarmCreationState = alarmCreationState,
                updateAlarmCreationState = { alarmActions.updateAlarmCreationState(it) },
                cardContainerColor = cardContainerColor
            )

            CustomizeAlarmEvent(
                modifier = Modifier
                    .fillMaxWidth(),
                alarmCreationState = alarmCreationState,
                updateAlarmCreationState = { alarmActions.updateAlarmCreationState(it) }
            )
            Buttons(

                navigateToAlarmList = navigateToAlarmList,
                save = navigateToAlarmList

            )
        }
    }
}

@Composable
private fun CustomizeAlarmEvent(
    modifier: Modifier,
    alarmCreationState: Alarm,
    updateAlarmCreationState: (Alarm) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = md_theme_light_primaryContainer, shape = RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            WeekDays(
                modifier = Modifier.fillMaxWidth(),
                alarmCreationState = alarmCreationState,
                updateAlarmCreationState = updateAlarmCreationState,
            )

        }
    }
}

@Composable
fun AlarmPicker(
    alarmCreationState: Alarm,
    updateAlarmCreationState: (Alarm) -> Unit,
    modifier: Modifier = Modifier,
    cardContainerColor: Color,
) {
    val textType = MaterialTheme.typography.displaySmall

    var hours by rememberSaveable( stateSaver = TextFieldValue.Saver ) {
        mutableStateOf(TextFieldValue(alarmCreationState.hour))
    }

    var minutes by rememberSaveable( stateSaver = TextFieldValue.Saver ) {
        mutableStateOf(TextFieldValue(alarmCreationState.minute))
    }

    Box(
        modifier = modifier
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NumberPicker(
                modifier = Modifier.weight(1f),
                number = hours,
                onNumberChange = { value ->
                    if ( value.text.checkNumberPicker(maxNumber = 23)) {
                        hours = value
                        updateAlarmCreationState(alarmCreationState.copy(hour = hours.text))
                    }
                },
                textStyle = textType,
                backgroundColor = cardContainerColor,
            )

            Text(
                text = ":",
                style = textType,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically),
            )

            NumberPicker(
                modifier = Modifier.weight(1f),
                number = minutes,
                textStyle = textType,
                backgroundColor = cardContainerColor,
                onNumberChange = { value ->
                    if (value.text.checkNumberPicker(maxNumber = 59)) {
                        minutes = value
                        updateAlarmCreationState(alarmCreationState.copy(minute = minutes.text))
                    }
                },
            )
        }
    }
}

fun String?.parseInt(): Int {
    return if (this.isNullOrEmpty()) 0 else this.toInt()
}

fun String.checkNumberPicker(maxNumber: Int): Boolean {
    return this.length <= 2 && this.isDigitsOnly() && this.parseInt() <= maxNumber
}

@Composable
private fun WeekDays(
    modifier: Modifier = Modifier,
    alarmCreationState: Alarm,
    updateAlarmCreationState: (Alarm) -> Unit,
) {
    val daysSelected = remember {
        mutableStateMapOf<String, Boolean>().apply {
            putAll(alarmCreationState.daysSelected)
        }
    }

    val orderedDays = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")

    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ){
        orderedDays.forEach { day ->
            CustomChip(
                isChecked = daysSelected[day] == true,
                text = day,
                onChecked = { isChecked ->
                    daysSelected[day] = isChecked
                    val activeDays = daysSelected.filterValues { it }.keys;
                    updateAlarmCreationState(
                        alarmCreationState.copy(
                            daysSelectedJson = Gson().toJson(daysSelected)
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun Buttons(
    modifier: Modifier = Modifier,
    navigateToAlarmList: () -> Unit,
    save: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    navigateToAlarmList()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                )
            }
            TextButton(
                onClick = {
                    save()
                    navigateToAlarmList()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                )
            }
        }
    }
}


