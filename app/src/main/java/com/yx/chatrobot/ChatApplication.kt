package com.yx.chatrobot

import android.app.Application
import android.content.Context
import com.yx.chatrobot.data.AppContainer
import com.yx.chatrobot.data.AppDataContainer

class ChatApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}