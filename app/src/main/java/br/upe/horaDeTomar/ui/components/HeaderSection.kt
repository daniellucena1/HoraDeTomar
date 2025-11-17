package br.upe.horaDeTomar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.navigation.TopLevelsDestinations
import br.upe.horaDeTomar.ui.themes.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    navController: NavController,
    userName: String,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()
    val currentTopLevel = remember(currentRoute) {
        TopLevelsDestinations.bottomNavItems.firstOrNull { it.route == currentRoute }
    }
    val isHome = currentRoute == TopLevelsDestinations.Home.route

    val shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
    val contentStart = 24.dp
    val titleGapFromIcon = 12.dp

    val collapsedFraction = if (isHome) (scrollBehavior?.state?.collapsedFraction ?: 0f) else 1f
    val bubbleAlphaA = (0.08f * (1f - collapsedFraction)).coerceIn(0f, 0.08f)
    val bubbleAlphaB = (0.06f * (1f - collapsedFraction)).coerceIn(0f, 0.06f)

    val appBarModifier = modifier
        .clip(shape)
        .drawBehind {
            drawRect(brush = Brush.verticalGradient(listOf(md_theme_light_primary, green_primary)))
            drawCircle(
                color = Color.White.copy(alpha = bubbleAlphaA),
                radius = size.minDimension * 0.45f,
                center = Offset(x = size.width * -0.05f, y = size.height * 0.2f)
            )
            drawCircle(
                color = Color.White.copy(alpha = bubbleAlphaB),
                radius = size.minDimension * 0.35f,
                center = Offset(x = size.width * 1.05f, y = size.height * 0.15f)
            )
        }

    if (isHome) {
        MediumTopAppBar(
            modifier = appBarModifier,
            scrollBehavior = scrollBehavior,
            windowInsets = TopAppBarDefaults.windowInsets,
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                navigationIconContentColor = white,
                titleContentColor = white
            ),
            navigationIcon = {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .padding(start = contentStart)
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(green_background)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = currentTopLevel?.icon ?: R.drawable.ic_user),
                        contentDescription = null,
                        tint = white,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            title = {
                val startSize = 22.sp
                val endSize = 18.sp
                val size = lerp(startSize, endSize, collapsedFraction)
                Text(
                    text = "Olá, ${userName.ifBlank { "visitante" }}",
                    fontSize = size,
                    fontWeight = FontWeight.SemiBold,
                    color = white,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = titleGapFromIcon)
                )
            }
        )
    } else {
        TopAppBar(
            modifier = appBarModifier,
            scrollBehavior = scrollBehavior,
            windowInsets = TopAppBarDefaults.windowInsets,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                navigationIconContentColor = white,
                titleContentColor = white
            ),
            navigationIcon = {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .padding(start = contentStart)
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(green_background)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = currentTopLevel?.icon ?: R.drawable.ic_user),
                        contentDescription = null,
                        tint = white,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            title = { Spacer(Modifier) }
        )
    }
}
