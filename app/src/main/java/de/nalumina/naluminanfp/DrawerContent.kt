package de.nalumina.naluminanfp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Edit

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState, scope: CoroutineScope) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text("Menü", style = MaterialTheme.typography.headlineSmall, color = textColor, modifier = Modifier.padding(16.dp))

        DrawerItem("Startseite", Icons.Default.Home, textColor) {
            navController.navigate(Screen.Home.route)
            scope.launch { drawerState.close() }
        }
        DrawerItem("Daten eingeben", Icons.Default.Edit, textColor) {
            navController.navigate(Screen.Input.route)
            scope.launch { drawerState.close() }
        }
        DrawerItem("Einstellungen", Icons.Default.Settings, textColor) {
            navController.navigate(Screen.Settings.route)
            scope.launch { drawerState.close() }
        }
    }
}

@Composable
fun DrawerItem(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, textColor: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(icon, contentDescription = text, tint = textColor, modifier = Modifier.padding(end = 8.dp))
        Text(text, color = textColor, style = MaterialTheme.typography.bodyLarge)
    }
}
