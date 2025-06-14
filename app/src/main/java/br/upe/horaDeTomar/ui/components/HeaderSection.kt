package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upe.horaDeTomar.R
import com.upe.horaDeTomar.ui.themes.HoraDoRemedioTheme
import com.upe.horaDeTomar.ui.themes.green_background
import com.upe.horaDeTomar.ui.themes.green_primary
import com.upe.horaDeTomar.ui.themes.white

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    hSize: Int = 110,
    mainIcon: String,
    userName: String,
) {
    val context = LocalContext.current

    val iconResId = remember (mainIcon) {
        context.resources.getIdentifier(
            mainIcon,
            "drawable",
            context.packageName
        )
    }

    val showGreenting = userName.isNotEmpty() && hSize != 110

    Column (
        modifier = modifier
            .fillMaxWidth()
            .height(hSize.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )
            )
            .background(
                color = green_primary,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = if (showGreenting) 0.dp else 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = green_background,
                            shape = CircleShape
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (iconResId != 0) {
                        Icon(
                            painter = painterResource(id = iconResId),
                            contentDescription = "Ícone de Tela",
                            modifier = Modifier.fillMaxWidth(),
                            tint = white
                        )
                    }

                }

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = green_background,
                            shape = CircleShape
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Ícone de Adicionar Usuário",
                        modifier = Modifier.fillMaxWidth(),
                        tint = white
                    )
                }
            }
        }
        if (showGreenting) {
            Text(
                text = "Olá, $userName!",
                color = white,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun HeaderSectionPreview() {
    HoraDoRemedioTheme {
        HeaderSection(
            mainIcon = "ic_user",
            userName = "Daniel",
            hSize = 120
        )
    }
}