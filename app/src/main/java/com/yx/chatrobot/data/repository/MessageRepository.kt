package com.yx.chatrobot.data.repository

import com.yx.chatrobot.data.entity.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getMessagesStreamByUserId(userId: Int): Flow<List<Message>>

    suspend fun insertMessage(message: Message)


}