package com.yx.chatrobot

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.yx.chatrobot.data.AppContainer
import com.yx.chatrobot.data.AppDataContainer
import com.yx.chatrobot.data.repository.UserPreferencesRepository

private const val USER_PREFERENCE_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)

class ChatApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }

}