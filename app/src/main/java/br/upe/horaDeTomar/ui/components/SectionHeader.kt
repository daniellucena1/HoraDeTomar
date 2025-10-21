package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import br.upe.horaDeTomar.ui.themes.button_green_primary
import br.upe.horaDeTomar.ui.themes.md_theme_light_onSurface
import br.upe.horaDeTomar.ui.themes.white

@Composable
fun SectionHeader(
    title: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = md_theme_light_onSurface,
            modifier = Modifier.weight(1f)
        )
        if (actionLabel != null && onAction != null) {
            AssistChip(
                onClick = onAction,
                label = { Text(actionLabel) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = button_green_primary, // #4CAF50
                    labelColor = white                      // #FFFFFF
                )
            )
        }
    }
}