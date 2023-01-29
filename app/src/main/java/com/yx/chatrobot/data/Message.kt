package com.yx.chatrobot.data

import androidx.annotation.DrawableRes
import com.yx.chatrobot.R

data class Message(
    @DrawableRes val imageResourceId: Int,
    val name: String, // 昵称
    val time: Long, // 时间
    val content: String, // 内容
    val isSelf: Boolean // 是否为本人（否则为 AI 回复）
)


