package com.example.idosodev.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.idosodev.R
import com.example.idosodev.ui.themes.HoraDoRemedioTheme
import com.example.idosodev.ui.themes.black
import com.example.idosodev.ui.themes.green_background
import com.example.idosodev.ui.themes.green_primary
import com.example.idosodev.ui.themes.green_secondary
import com.example.idosodev.ui.themes.white

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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(hSize.dp)
            .shadow(6.dp, shape = RoundedCornerShape(12.dp))
            .background(
                color = green_primary,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
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

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Olá, $userName!",
            color = white,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun HeaderSectionPreview() {
    HoraDoRemedioTheme {
        HeaderSection(
            mainIcon = "ic_user",
            userName = "Daniel"
        )
    }
}