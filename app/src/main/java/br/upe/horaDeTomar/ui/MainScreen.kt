@file:OptIn(ExperimentalMaterial3Api::class)

package br.upe.horaDeTomar.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.upe.horaDeTomar.navigation.BottomBarNav
import br.upe.horaDeTomar.navigation.TopLevelsDestinations
import br.upe.horaDeTomar.ui.components.HeaderSection
import br.upe.horaDeTomar.ui.homePage.HomePageScreen
import br.upe.horaDeTomar.ui.medications.MedicationsScreen
import br.upe.horaDeTomar.ui.medications.RegisterMedicineScreen
import br.upe.horaDeTomar.ui.reminders.RemindersScreen
import br.upe.horaDeTomar.ui.settings.SettingsScreen
import br.upe.horaDeTomar.ui.users.UserRegisterScreen
import br.upe.horaDeTomar.ui.users.UsersScreen

@Composable
fun MainScreen(viewModel: AccountViewModel = hiltViewModel()) {

    val hasAccount by viewModel.hasAccount.collectAsStateWithLifecycle()
    val accounts by viewModel.accounts.collectAsStateWithLifecycle()

    val navController = rememberNavController()

    val backStackEntryState = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntryState.value?.destination?.route ?: ""

    val hideBarsOn = setOf("registerUser", "registerMedication")
    val topLevelRoutes = TopLevelsDestinations.bottomNavItems.map { it.route }.toSet()

    val showTopBar = currentRoute !in hideBarsOn
    val showBottomBar = currentRoute in topLevelRoutes

    LaunchedEffect(hasAccount) {
        if (!hasAccount && currentRoute != "registerUser") {
            navController.navigate("registerUser") {
                popUpTo(TopLevelsDestinations.Home.route) { inclusive = false }
                launchSingleTop = true
            }
        } else if (hasAccount && currentRoute == "registerUser") {
            navController.navigate(TopLevelsDestinations.Home.route) {
                popUpTo("registerUser") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        topBar = {
            if (showTopBar) {
                HeaderSection(
                    navController = navController,
                    userName = accounts.firstOrNull()?.accountName.orEmpty()
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomBarNav(
                    navController = navController,
                    topLevelRoutes = TopLevelsDestinations.bottomNavItems
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TopLevelsDestinations.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TopLevelsDestinations.Home.route) {
                HomePageScreen(navController = navController)
            }
            composable(TopLevelsDestinations.Medications.route) {
                MedicationsScreen(navController = navController)
            }
            composable(TopLevelsDestinations.Reminders.route) {
                RemindersScreen(navController = navController)
            }
            composable(TopLevelsDestinations.Users.route) {
                UsersScreen(navController = navController)
            }
            composable(TopLevelsDestinations.Settings.route) { SettingsScreen() }

            composable("registerMedication") {
                RegisterMedicineScreen(navControler = navController)
            }
            composable("registerUser") {
                UserRegisterScreen(
                    onUserRegistered = {
                        navController.navigate(TopLevelsDestinations.Home.route) {
                            popUpTo("registerUser") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navController = navController,
                    isFirstTime = !hasAccount
                )
            }
            composable("editMedication/{medicationId}") { /* ... */ }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun MainScreenPreview() {
    MainScreen()
}