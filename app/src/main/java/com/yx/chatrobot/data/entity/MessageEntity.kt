package com.yx.chatrobot.data.entity

data class MessageEntity(
    val name: String,
    val time: Int,
    val content: String,
    val UserId: Int, // 对应的用户 Id
    val isSelf: Boolean
)