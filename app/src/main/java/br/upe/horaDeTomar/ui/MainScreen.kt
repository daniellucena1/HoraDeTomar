package br.upe.horaDeTomar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.upe.horaDeTomar.navigation.BottomBarNav
import br.upe.horaDeTomar.navigation.TopLevelsDestinations
import br.upe.horaDeTomar.ui.components.HeaderSection
import br.upe.horaDeTomar.ui.homePage.HomePageScreen
import br.upe.horaDeTomar.ui.medications.MedicationsScreen
import br.upe.horaDeTomar.ui.reminders.RemindersScreen
import br.upe.horaDeTomar.ui.settings.SettingsScreen
import br.upe.horaDeTomar.ui.users.UsersScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController();

    Column (modifier = Modifier.fillMaxSize()) {
        Scaffold (
            modifier = Modifier.fillMaxWidth(),

            topBar = {
                HeaderSection(
                    navController = navController
                )
            },

            bottomBar = {
                BottomBarNav(
                    navController = navController,
                    topLevelRoutes = TopLevelsDestinations.bottomNavItems
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = TopLevelsDestinations.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(TopLevelsDestinations.Home.route) { HomePageScreen() }
                composable(TopLevelsDestinations.Medications.route) { MedicationsScreen() }
                composable(TopLevelsDestinations.Reminders.route) { RemindersScreen() }
                composable(TopLevelsDestinations.Users.route) { UsersScreen() }
                composable(TopLevelsDestinations.Settings.route) { SettingsScreen() }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun MainScreenPreview() {
    MainScreen()
}