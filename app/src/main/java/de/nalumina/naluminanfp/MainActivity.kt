package de.nalumina.naluminanfp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import de.nalumina.naluminanfp.ui.theme.NaluminaNFPTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themePreferences = ThemePreferences(this)
            val scope = rememberCoroutineScope()
            var themeMode by remember { mutableStateOf(0) }

            LaunchedEffect(Unit) {
                themePreferences.themeModeFlow.collect { mode ->
                    themeMode = mode
                }
            }

            val darkTheme = when (themeMode) {
                1 -> false // Hellmodus
                2 -> true  // Dunkelmodus
                else -> isSystemInDarkTheme() // Systemstandard
            }

            NaluminaNFPTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = true,
                    drawerContent = {
                        Box(modifier = Modifier.fillMaxWidth(0.75f)) { // Menü auf 75% der Breite begrenzen
                            DrawerContent(navController, drawerState, scope)
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { AppTopBar(scope, drawerState) }
                    ) { innerPadding ->
                        AppNavHost(navController, this@MainActivity) // Context weitergeben
                    }
                }
            }
        }
    }
}
