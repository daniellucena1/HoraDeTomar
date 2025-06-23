package br.upe.horaDeTomar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import br.upe.horaDeTomar.navigation.bottomNavItems
import br.upe.horaDeTomar.ui.components.HeaderSection
import br.upe.horaDeTomar.ui.homePage.HomePageScreen
import br.upe.horaDeTomar.ui.medications.MedicationsScreen
import br.upe.horaDeTomar.ui.medicineRegister.RegisterMedicineScreen
import br.upe.horaDeTomar.ui.reminders.RemindersScreen
import br.upe.horaDeTomar.ui.settings.SettingsScreen
import br.upe.horaDeTomar.ui.themes.black
import br.upe.horaDeTomar.ui.themes.green_primary
import br.upe.horaDeTomar.ui.themes.white
import br.upe.horaDeTomar.ui.userResgister.UserRegisterScreen
import br.upe.horaDeTomar.ui.users.UsersScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    var icons = listOf(
        "ic_home",
        "ic_pill",
        "ic_calendar",
        "ic_user",
        "ic_settings"
    )

    Column (modifier = Modifier.fillMaxSize()) {
        Scaffold (
            modifier = Modifier.fillMaxWidth(),

            topBar = {
                HeaderSection(
                    mainIcon = icons[selectedIndex],
                    userName = if (selectedIndex == 0) "Daniel Torres" else "",
                    hSize = if (selectedIndex == 0) 140 else 110,
                )
            },

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
            contentScreen(modifier = Modifier.padding(innerPadding).fillMaxSize(), selectedScreen = selectedIndex)
        }
    }


}

@Composable
fun contentScreen(modifier: Modifier = Modifier, selectedScreen: Int) {
    when(selectedScreen) {
        0 -> HomePageScreen(modifier = modifier)
        1 -> MedicationsScreen(modifier = modifier)
        2 -> RemindersScreen(modifier = modifier)
        3 -> UsersScreen(modifier = modifier)
        4 -> SettingsScreen(modifier = modifier)
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun MainScreenPreview() {
    MainScreen()
}