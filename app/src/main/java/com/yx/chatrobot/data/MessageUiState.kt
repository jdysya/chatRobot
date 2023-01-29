package com.yx.chatrobot.data

import androidx.annotation.DrawableRes
import com.yx.chatrobot.R
import com.yx.chatrobot.data.entity.Message

data class MessageUiState(
    val id: Int = 0,
    val name: String = "", // 昵称
    val time: Long = 1674973683, // 时间
    val content: String = "", // 内容
    val isSelf: Boolean = false // 是否为本人（否则为 AI 回复）
)

fun MessageUiState.toMessage(): Message = Message(
    id = id,
    name = name,
    time = time,
    content = content,
    userId = 1314,
    isSelf = isSelf
)

fun Message.toMessageUiState(): MessageUiState = MessageUiState(
    id = id,
    name = name,
    time = time,
    content = content,
    isSelf = isSelf
)








