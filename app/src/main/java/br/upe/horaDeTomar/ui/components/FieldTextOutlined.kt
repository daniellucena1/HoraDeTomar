package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import br.upe.horaDeTomar.ui.config.OutlinedInputConfig
import br.upe.horaDeTomar.ui.themes.*

@Composable
fun FieldTextOutlined(
    value: String,
    onChange: (String) -> Unit,
    config: OutlinedInputConfig,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
    placeholder: String? = null,
    leadingPainter: Painter? = null,
    trailingContent: (@Composable (() -> Unit))? = null,
    readOnly: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(config.label) },
        placeholder = { if (placeholder != null) Text(placeholder) },
        supportingText = {
            if (isError) {
                Text(text = "Campo obrigatório", color = MaterialTheme.colorScheme.error)
            }
        },
        isError = isError,
        readOnly = readOnly,
        modifier = modifier.padding(contentPadding),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = config.capitalization,
            keyboardType = config.keyboardType
        ),
        leadingIcon = if (leadingPainter != null) {
            { Icon(painter = leadingPainter, contentDescription = null, tint = text_balck) }
        } else null,
        trailingIcon = trailingContent,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = white,
            unfocusedContainerColor = white,
            disabledContainerColor = md_theme_light_surface,
            focusedBorderColor = button_green_primary,
            unfocusedBorderColor = md_theme_light_outline,
            errorBorderColor = MaterialTheme.colorScheme.error,
            cursorColor = button_green_primary,
            focusedLabelColor = button_green_primary,
            unfocusedLabelColor = text_balck
        )
    )
}
