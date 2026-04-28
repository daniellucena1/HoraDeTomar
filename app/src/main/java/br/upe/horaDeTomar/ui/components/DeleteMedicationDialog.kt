package br.upe.horaDeTomar.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import br.upe.horaDeTomar.ui.themes.button_green_primary
import br.upe.horaDeTomar.ui.themes.md_theme_error

@Composable
fun DeleteMedicationDialog(
    medicationName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Excluir Medicamento") },
        text = { Text(text = "Tem certeza que deseja excluir o medicamento \"$medicationName\"? Esta ação não pode ser desfeita.") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = md_theme_error)
            ) {
                Text("Excluir", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = button_green_primary)
            }
        }
    )
}
