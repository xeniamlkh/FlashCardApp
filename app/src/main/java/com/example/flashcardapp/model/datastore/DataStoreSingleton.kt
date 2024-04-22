package com.example.flashcardapp.model.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES = "user_preferences"
private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = USER_PREFERENCES)

class DataStoreSingleton(val context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveThemeToDataStore(isNightMode: Boolean) {
        dataStore.edit { preferences -> preferences[THEME_MODE_KEY] = isNightMode }
    }

    val themeMode: Flow<Boolean> = dataStore.data.map { preferences ->
        val themeMode = preferences[THEME_MODE_KEY] ?: false
        themeMode
    }

    companion object {
        private val THEME_MODE_KEY = booleanPreferencesKey("theme_mode_key")
    }
}
