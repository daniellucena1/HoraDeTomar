package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.upe.horaDeTomar.ui.config.OutlinedInputConfig

@Composable
fun FieldTextOutlined(
    value: String,
    onChange: (String) -> Unit,
    config: OutlinedInputConfig,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(config.label) },
        supportingText = {
            if (isError) {
                Text(text = "Campo obrigatório", color = MaterialTheme.colorScheme.error)
            }
        },
        isError = isError,
        modifier = modifier
            .padding(contentPadding),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = config.capitalization,
            keyboardType = config.keyboardType
        )
    )
}
