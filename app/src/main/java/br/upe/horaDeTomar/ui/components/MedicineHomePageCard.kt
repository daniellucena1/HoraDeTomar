package br.upe.horaDeTomar.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.ui.themes.HoraDoRemedioTheme
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.themes.green_primary
import br.upe.horaDeTomar.ui.themes.green_secondary
import coil.compose.AsyncImage
import androidx.core.net.toUri

@Composable
fun MedicineHomePageCard(
    medicineName: String,
    dose: String,
    time: String,
    imageUri: String
) {
    Row {
        if (imageUri != "") {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(6.dp))
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUri.toUri(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(6.dp))
                    .background(
                        color = green_secondary,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pill),
                    contentDescription = "Ícone de Usuário",
                    modifier = Modifier.fillMaxWidth(),
                    tint = black
                )
            }
        }

        Column (
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = medicineName,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis
            )

            Row {
                Text(
                    text = "Dosagem: $dose",
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = green_primary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Horário: $time",
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = green_primary,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun MedicineHomePageCardPreview() {
//    HoraDoRemedioTheme {
//        MedicineHomePageCard(
//            medicineName = "Dipirona",
//            dose = "1 comprimido",
//            time = "12:00"
//        )
//    }
//}