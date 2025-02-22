package de.nalumina.naluminanfp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.nalumina.naluminanfp.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Willkommen zu Nalumina NFP!", modifier = Modifier.padding(16.dp))

        Button(onClick = { navController.navigate(Screen.Input.route) }) {
            Text("Daten eintragen")
        }
    }
}
