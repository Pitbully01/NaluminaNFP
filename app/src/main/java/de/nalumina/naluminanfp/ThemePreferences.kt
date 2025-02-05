package de.nalumina.naluminanfp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferences(private val context: Context) {
    companion object {
        val THEME_MODE_KEY = intPreferencesKey("theme_mode")
    }

    val themeModeFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE_KEY] ?: 0 // Standard: Systemstandard
    }

    suspend fun setThemeMode(mode: Int) {
        context.dataStore.edit { settings ->
            settings[THEME_MODE_KEY] = mode
        }
    }
}
