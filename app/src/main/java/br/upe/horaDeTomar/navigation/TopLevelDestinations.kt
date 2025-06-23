package br.upe.horaDeTomar.navigation

import br.upe.horaDeTomar.R

sealed class TopLevelsDestinations(val route: String, val label: String, val icon: Int) {
    object Home : TopLevelsDestinations("home", "Início", R.drawable.ic_home)
    object Medications : TopLevelsDestinations("medications", "Medicamentos", R.drawable.ic_pill)
    object Reminders : TopLevelsDestinations("reminders", "Agenda", R.drawable.ic_calendar)
    object Users : TopLevelsDestinations("users", "Usuários", R.drawable.ic_user)
    object Settings : TopLevelsDestinations("settings", "Configurações", R.drawable.ic_settings)

    companion object {
        val bottomNavItems = listOf(
            TopLevelsDestinations.Home,
            TopLevelsDestinations.Medications,
            TopLevelsDestinations.Reminders,
            TopLevelsDestinations.Users,
            TopLevelsDestinations.Settings
        )
    }
}

