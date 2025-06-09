package com.example.idosodev.presentation.components

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.idosodev.R // Certifique-se de ter o ID do estilo do diálogo aqui
import com.example.idosodev.presentation.ui.theme.GreenPrimary // Importe sua cor primária
import java.util.Calendar
import java.util.Locale

@Composable
fun TimePicker(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (String) -> Unit,
    showDialog: Boolean,
    onDismissRequest: () -> Unit
) {

    val context = LocalContext.current

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            R.style.MyTimePickerDialogTheme,
            { _, selectedHour, selectedMinute ->
                val selectedTime = String.Companion.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    selectedHour,
                    selectedMinute
                )
                onTimeSelected(selectedTime)
                onDismissRequest()
            },
            initialHour,
            initialMinute,
            true
        )
    }

    timePickerDialog.setOnShowListener { dialogInterface ->
        val dialog = dialogInterface as TimePickerDialog
        val positiveButton = dialog.getButton(TimePickerDialog.BUTTON_POSITIVE)
        val negativeButton = dialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)

        positiveButton.setTextColor(ContextCompat.getColor(context, R.color.green_primary))
        negativeButton.setTextColor(ContextCompat.getColor(context, R.color.green_primary))
    }

    timePickerDialog.setOnCancelListener {
        onDismissRequest()
    }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            timePickerDialog.show()
        } else {
            if (timePickerDialog.isShowing) {
                timePickerDialog.dismiss()
            }
        }
    }
}