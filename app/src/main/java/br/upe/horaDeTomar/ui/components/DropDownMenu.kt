package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.upe.horaDeTomar.ui.themes.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    options: List<String>,
    label: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean,
    contentPadding: PaddingValues,
    selectedText: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var internalSelected by remember { mutableStateOf(selectedText ?: "") }

    Column(modifier = modifier.padding(contentPadding)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = internalSelected,
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .padding(0.dp),
                isError = isError,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = white,
                    unfocusedContainerColor = white,
                    disabledContainerColor = md_theme_light_surface,
                    focusedBorderColor = button_green_primary,
                    unfocusedBorderColor = md_theme_light_outline,
                    errorBorderColor = md_theme_error,
                    cursorColor = button_green_primary,
                    focusedLabelColor = button_green_primary,
                    unfocusedLabelColor = text_balck
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt) },
                        onClick = {
                            internalSelected = opt
                            expanded = false
                            onSelect(opt)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        if (isError) {
            Text(
                text = "Campo obrigatório",
                color = md_theme_error,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
