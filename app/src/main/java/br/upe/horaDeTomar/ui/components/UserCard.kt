package br.upe.horaDeTomar.ui.components

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
import androidx.compose.ui.draw.shadow
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

@Composable
fun UserCard(
    userName: String,
    age: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
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
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "Ícone de Usuário",
                modifier = Modifier.fillMaxWidth(),
                tint = black
            )
        }

        Column (
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = userName,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$age anos de idade",
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                color = green_primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    HoraDoRemedioTheme {
        UserCard(
            userName = "Daniel",
            age = 21

        )
    }
}