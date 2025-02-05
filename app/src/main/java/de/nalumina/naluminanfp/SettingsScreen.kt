package de.nalumina.naluminanfp

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController, context: Context) {
    val themePreferences = ThemePreferences(context)
    val scope = rememberCoroutineScope()
    var selectedTheme by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        themePreferences.themeModeFlow.collect { mode ->
            selectedTheme = mode
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Einstellungen", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Theme wählen:")

        var expanded by remember { mutableStateOf(false) }
        val themeOptions = listOf("Systemstandard", "Hell", "Dunkel")

        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(themeOptions[selectedTheme])
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                themeOptions.forEachIndexed { index, text ->
                    DropdownMenuItem(text = { Text(text) }, onClick = {
                        selectedTheme = index
                        scope.launch { themePreferences.setThemeMode(index) }
                        expanded = false
                    })
                }
            }
        }
    }
}
