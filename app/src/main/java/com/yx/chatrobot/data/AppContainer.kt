package com.yx.chatrobot.data

import android.content.Context
import com.yx.chatrobot.data.repository.*

interface AppContainer {
    val messageRepository: MessageRepository
    val userRepository: UserRepository
    val configRepository: ConfigRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val messageRepository: MessageRepository by lazy {
        OfflineMessageRepository(ChatDb.getDatabase(context).messageDao)
    }
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(ChatDb.getDatabase((context)).userDao)
    }

    override val configRepository: ConfigRepository by lazy {
        OfflineConfigRepository(ChatDb.getDatabase(context).configDao)
    }
}