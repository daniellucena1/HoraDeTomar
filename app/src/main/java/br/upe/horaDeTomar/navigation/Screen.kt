package br.upe.horaDeTomar.navigation

import br.upe.horaDeTomar.R

sealed class Screen(val route: String, val label: String, val icon: Int) {
    object Home : Screen("home", "Início", R.drawable.ic_home)
    object Medications : Screen("medications", "Medicamentos", R.drawable.ic_pill)
    object Reminders : Screen("reminders", "Agenda", R.drawable.ic_calendar)
    object Users : Screen("users", "Usuários", R.drawable.ic_user)
    object Settings : Screen("settings", "Configurações", R.drawable.ic_settings)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Medications,
    Screen.Reminders,
    Screen.Users,
    Screen.Settings
)