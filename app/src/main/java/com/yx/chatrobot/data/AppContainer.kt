package com.yx.chatrobot.data

import android.content.Context
import com.yx.chatrobot.data.repository.MessageRepository
import com.yx.chatrobot.data.repository.OfflineMessageRepository
import com.yx.chatrobot.data.repository.OfflineUserRepository
import com.yx.chatrobot.data.repository.UserRepository

interface AppContainer {
    val messageRepository: MessageRepository
    val userRepository: UserRepository
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
}