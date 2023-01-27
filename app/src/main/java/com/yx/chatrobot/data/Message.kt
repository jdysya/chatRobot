package com.yx.chatrobot.data

import androidx.annotation.DrawableRes
import com.yx.chatrobot.R

data class Message(
    @DrawableRes val imageResourceId: Int,
    val name: String,
    val time: Long,
    val content: String
)


