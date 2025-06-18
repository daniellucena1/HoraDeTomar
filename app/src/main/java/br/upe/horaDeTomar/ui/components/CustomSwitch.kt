package br.upe.horaDeTomar.ui.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomSwitch(
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val thumbColor = Color.White
    val trackCheckedColor = Color(0xFFD0EEEE)
    val trackUncheckedColor = Color(0xFFD0EEEE)

    Switch(
        checked = isChecked,
        onCheckedChange = onCheckChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = thumbColor,
            checkedTrackColor = trackCheckedColor,
            uncheckedThumbColor = thumbColor,
            uncheckedTrackColor = trackUncheckedColor
        ),
        modifier = modifier
            .scale(1.2f)
    )
}

@Preview(showBackground = true)
@Composable
fun CustomSwitchPreview() {
    CustomSwitch(
        isChecked = true,
        onCheckChange = {}
    )
}