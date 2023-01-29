package com.yx.chatrobot.data.repository

import com.yx.chatrobot.data.dao.MessageDao
import com.yx.chatrobot.data.entity.Message
import kotlinx.coroutines.flow.Flow

class OfflineMessageRepository(private val messageDao: MessageDao) : MessageRepository {
    override fun getMessagesStreamByUserId(userId: Int): Flow<List<Message>> =
        messageDao.getAllMessagesByUserId(userId)

    override suspend fun insertMessage(message: Message) = messageDao.insert(message)
}