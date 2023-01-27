package com.yx.chatrobot.domain

data class RequestBody(
    val model: String,
    val prompt: String,
    val temperature: Int,
    val max_tokens: Int = 4000
)