package br.upe.horaDeTomar.ui.components

import android.widget.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.ui.themes.green_primary
import br.upe.horaDeTomar.ui.themes.white
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.upe.horaDeTomar.ui.themes.black

@Composable
fun OptionsCard(
    iconName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String
) {
    val context = LocalContext.current

    val iconResId = remember (iconName) {
        context.resources.getIdentifier(
            iconName,
            "drawable",
            context.packageName
        )
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .width(256.dp)
            .height(80.dp)
            .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 16.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = green_primary,
            contentColor = white
        ),
        contentPadding = ButtonDefaults.ContentPadding
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "Ícone de $label",
            modifier = Modifier
                .padding(end = 8.dp),
            tint = black
        )
        Text(
            text = label,
            fontSize = 18.sp,
            color = black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OptionsCardPreview() {
    OptionsCard(
        iconName = "ic_pill",
        label = "Adicionar Remédio",
        onClick = {}
    )
}