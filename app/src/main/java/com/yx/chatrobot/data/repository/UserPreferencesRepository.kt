package com.yx.chatrobot.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val FONT_SIZE = stringPreferencesKey("font_size")
        const val TAG = "UserPreferencesRepo"
    }

    val themeConfig: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_DARK_THEME] ?: false
        }

    val fontConfig: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[FONT_SIZE] ?: "小"
        }

    suspend fun saveUserPreference(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = value
        }
    }

    suspend fun saveUserFontPreference(value: String) {
        dataStore.edit { preferences ->
            preferences[FONT_SIZE] = value
        }
    }
}