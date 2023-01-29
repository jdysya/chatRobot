package com.yx.chatrobot.data

import android.content.Context
import com.yx.chatrobot.data.repository.MessageRepository
import com.yx.chatrobot.data.repository.OfflineMessageRepository

interface AppContainer {
    val messageRepository: MessageRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val messageRepository: MessageRepository by lazy {
        OfflineMessageRepository(ChatDb.getDatabase(context).messageDao)
    }
}