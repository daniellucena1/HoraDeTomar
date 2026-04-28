package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.core.net.toUri
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.ui.themes.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicineHomePageCard(
    medicineName: String,
    dose: String,
    time: String,
    imageUri: String = "",
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    val times = time.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    val timesText = if (times.isEmpty()) "--" else times.joinToString(" · ")

    Box(modifier = modifier) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { onClick?.invoke() },
                    onLongClick = { if (onDelete != null || onEdit != null) expanded = true }
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = white, contentColor = text_balck)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (imageUri.isNotBlank()) {
                    AsyncImage(
                        model = imageUri.toUri(),
                        contentDescription = "Imagem do medicamento",
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.ic_pill),
                        error = painterResource(id = R.drawable.ic_pill)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(green_secondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pill),
                            contentDescription = null,
                            tint = text_balck
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = medicineName,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = text_balck
                    )

                    Spacer(Modifier.height(8.dp))

                    StatItem(
                        iconRes = R.drawable.ic_pill,
                        label = "Dose",
                        value = dose
                    )

                    Spacer(Modifier.height(8.dp))

                    StatItem(
                        iconRes = R.drawable.alarm_clock,
                        label = "Horário",
                        value = timesText
                    )
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Editar") },
                onClick = {
                    expanded = false
                    onEdit?.invoke()
                }
            )
            DropdownMenuItem(
                text = { Text("Excluir", color = Color.Red) },
                onClick = {
                    expanded = false
                    onDelete?.invoke()
                }
            )
        }
    }
}

@Composable
private fun StatItem(
    iconRes: Int,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = gray_dark,
            modifier = Modifier
                .size(20.dp)
                .padding(end = 8.dp)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = gray_dark
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = text_balck
            )
        }
    }
}
