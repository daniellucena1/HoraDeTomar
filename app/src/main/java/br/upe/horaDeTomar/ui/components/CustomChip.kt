package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.themes.green_background
import br.upe.horaDeTomar.ui.themes.green_primary
import br.upe.horaDeTomar.ui.themes.md_theme_error
import br.upe.horaDeTomar.ui.themes.md_theme_light_outline
import br.upe.horaDeTomar.ui.themes.md_theme_light_secondary

@Composable
fun CustomChip(
    isChecked: Boolean,
    text: String,
    onChecked: (Boolean) -> Unit,
    selectedColor: Color = MaterialTheme.colorScheme.primary
) {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(35.dp)
            .padding(
                horizontal = 2.dp
            )
            .border(
                width = 0.5.dp,
                color = if (isChecked) {
                    selectedColor
                }else if (text == "Dom") {
                    md_theme_error
                } else {
                    black
                },
                shape = CircleShape
            )
            .background(
                color = if (isChecked) selectedColor else Color.Transparent,
                shape = CircleShape
            )
            .clip(shape = CircleShape)
            .clickable {
                onChecked(!isChecked)
            }
            .padding(4.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            color = if (text == "Dom") md_theme_error else black
        )
    }
}