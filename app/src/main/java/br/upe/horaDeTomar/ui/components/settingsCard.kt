package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.themes.green_card
import br.upe.horaDeTomar.ui.themes.green_primary
import br.upe.horaDeTomar.ui.themes.green_secondary

@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onClick: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 70.dp, max = 90.dp)
            .background(color = green_card, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row (
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = androidx.compose.ui.Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Lembretes de medicamentos" ,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Receba Lembretes diários para tomar seus medicamentos",
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = green_primary,
                )
            }
            CustomSwitch(
                isChecked = isChecked,
                onCheckChange = onClick,
                modifier = Modifier
                    .padding(end = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsCardPreview() {

}