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
        val ROBOT_NAME = stringPreferencesKey("robot_name")
        const val TAG = "UserPreferencesRepo"
    }

    val config = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[ROBOT_NAME] ?: "机器人"
        }

    suspend fun saveUserPreference() {
        dataStore.edit { preferences ->
            preferences[ROBOT_NAME] = "AI"
        }
    }
}