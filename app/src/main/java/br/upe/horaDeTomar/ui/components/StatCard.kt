package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.upe.horaDeTomar.ui.themes.gray_dark
import br.upe.horaDeTomar.ui.themes.green_card
import br.upe.horaDeTomar.ui.themes.text_balck

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    // Card clarinho que contrasta no degradê
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = green_card // #DCEEE0
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                color = gray_dark // #757575
            )
            Text(
                value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = text_balck // #57535C
            )
        }
    }
}

// Adicionei um Preview para que você possa visualizar o componente no Android Studio
@Preview
@Composable
private fun StatCardPreview() {
    StatCard(label = "Próximo Lembrete", value = "14:00h")
}
