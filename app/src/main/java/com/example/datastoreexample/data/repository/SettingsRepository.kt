package com.example.datastoreexample.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastoreexample.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val dataStore: DataStore<Preferences>
) {
    private object Keys {
        val USERNAME = stringPreferencesKey("username")
        val BOLD_TEXT = booleanPreferencesKey("bold_text")
        val FONT_SIZE = intPreferencesKey("font_size")
    }

    val settingsFlow: Flow<UserSettings> = dataStore.data
        .map { preferences ->
            UserSettings(
                username = preferences[Keys.USERNAME] ?: "",
                boldText = preferences[Keys.BOLD_TEXT] ?: false,
                fontSize = preferences[Keys.FONT_SIZE] ?: 16
            )
        }

    suspend fun updateUsername(username: String) {
        Log.v("Tag", username)
        dataStore.edit { preferences ->
            preferences[Keys.USERNAME] = username
        }
    }

    suspend fun updateBoldText(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.BOLD_TEXT] = enabled
        }
    }

    suspend fun updateFontSize(size: Int) {
        dataStore.edit { preferences ->
            preferences[Keys.FONT_SIZE] = size
        }
    }
}