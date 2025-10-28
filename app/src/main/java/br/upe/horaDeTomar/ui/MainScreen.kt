@file:OptIn(ExperimentalMaterial3Api::class)

package br.upe.horaDeTomar.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.foundation.background
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.foundation.layout.WindowInsets
import br.upe.horaDeTomar.ui.themes.green_background_register_medicine
import br.upe.horaDeTomar.ui.themes.green_secondary

@Composable
fun MainScreen(viewModel: AccountViewModel = hiltViewModel()) {
    val startup by viewModel.startup.collectAsStateWithLifecycle()

    when (val s = startup) {
        StartupState.Loading -> { /* ... */ }
        is StartupState.Ready -> {
            val hasAccount = s.hasAccount
            val userName = s.firstAccountName.orEmpty()

            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route.orEmpty()

            val hideBarsOn = setOf("registerUser", "registerMedication")
            val topLevelRoutes = TopLevelsDestinations.bottomNavItems.map { it.route }.toSet()

            val showTopBar = currentRoute !in hideBarsOn
            val showBottomBar = currentRoute in topLevelRoutes

            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    if (showTopBar) {
                        HeaderSection(
                            navController = navController,
                            userName = userName,
                            scrollBehavior = scrollBehavior
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
                },
                contentWindowInsets = WindowInsets(0.dp)
            ) { innerPadding ->
                Box(Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {

                    NavHost(
                        navController = navController,
                        startDestination = if (hasAccount) TopLevelsDestinations.Home.route else "registerUser",
                        modifier = Modifier.fillMaxSize()
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
                        composable("registerMedication") { RegisterMedicineScreen(navControler = navController) }
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
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun MainScreenPreview() {
    MainScreen()
}