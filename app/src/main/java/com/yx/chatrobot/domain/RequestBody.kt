package com.yx.chatrobot.domain

data class RequestBody(
    val model: String = "text-davinci-003",
    val prompt: String = "你好",
    val temperature: Double = 0.9,
    val top_p: Double = 1.0,
    val max_tokens: Int = 150,
    val frequency_penalty: Double = 0.0,
    val presence_penalty: Double = 0.6
)