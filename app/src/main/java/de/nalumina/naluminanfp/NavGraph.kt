package de.nalumina.naluminanfp

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Input : Screen("input")
    object Settings : Screen("settings")
}

@Composable
fun AppNavHost(navController: NavHostController, context: Context) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Input.route) { InputScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController, context) }
    }
}
