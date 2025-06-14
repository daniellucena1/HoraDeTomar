package br.upe.horaDeTomar.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upe.horaDeTomar.navigation.bottomNavItems
import com.upe.horaDeTomar.ui.homePage.HomePageScreen
import com.upe.horaDeTomar.ui.medicineRegister.RegisterMedicineScreen
import com.upe.horaDeTomar.ui.themes.black
import com.upe.horaDeTomar.ui.themes.green_primary
import com.upe.horaDeTomar.ui.themes.white
import com.upe.horaDeTomar.ui.userResgister.UserRegisterScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold (
        modifier = Modifier.fillMaxWidth(),
        bottomBar = {
            NavigationBar (
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            ){
                bottomNavItems.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
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
    ) { innerPadding ->
        contentScreen(modifier = Modifier.padding(innerPadding), selectedScreen = selectedIndex)
    }
}

@Composable
fun contentScreen(modifier: Modifier = Modifier, selectedScreen: Int) {
    when(selectedScreen) {
         0 -> HomePageScreen(modifier = modifier)
         1 -> RegisterMedicineScreen()
         3 -> UserRegisterScreen({}, {})
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun MainScreenPreview() {
    MainScreen()
}