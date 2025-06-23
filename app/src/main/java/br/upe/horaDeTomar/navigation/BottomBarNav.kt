package br.upe.horaDeTomar.navigation

import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.themes.green_primary
import br.upe.horaDeTomar.ui.themes.white

@Composable
fun BottomBarNav(
    navController: NavController,
    topLevelRoutes: List<TopLevelsDestinations>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar (
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
    ){
        topLevelRoutes.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.label
                    )
                },
                label = {
                    Text(
                        text = screen.label,
                        fontSize = 8.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = green_primary,
                    selectedIconColor = white,
                    selectedTextColor = black,
                    unselectedIconColor = black,
                    unselectedTextColor = black
                )
            )
        }
    }
}