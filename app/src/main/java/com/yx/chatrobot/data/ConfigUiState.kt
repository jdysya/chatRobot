package com.yx.chatrobot.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.yx.chatrobot.data.entity.Config
import com.yx.chatrobot.domain.RequestBody

data class ConfigUiState(
    val robotName: String = "AI",
    var model: String = "text-davinci-003",
    val maxTokens: Int = 60,
    val temperature: Double = 0.5,
    val topP: Double = 1.0,
    val stop: String = ""
)

fun Config.toConfigUiState() = ConfigUiState(
    robotName = robotName,
    model = model,
    maxTokens = maxTokens,
    temperature = temperature,
    topP = topP,
    stop = stop
)

fun ConfigUiState.toConfig(id: Int = 0, userId: Int) = Config(
    id = id,
    robotName = robotName,
    model = model,
    maxTokens = maxTokens,
    temperature = temperature,
    topP = topP,
    stop = stop,
    userId = userId
)

//fun ConfigUiState.toRequestBody(prompt: String) = RequestBody(
//    model = model,
//    max_tokens = maxTokens,
//    prompt = prompt,
//    temperature = temperature,
//    top_p = topP,
//    stop = stop
//)


val modelList = listOf(
    Pair("text-davinci-003", Icons.Filled.Settings),
    Pair("text-curie-001", Icons.Filled.Settings),
    Pair("text-babbage-001", Icons.Filled.Settings),
    Pair("text-ada-001", Icons.Filled.Settings)
)


val bottomDrawerList = listOf(
    Pair("Inbox", Icons.Filled.Inbox),
    Pair("Outbox", Icons.Filled.Outbox),
    Pair("Favorites", Icons.Filled.Favorite),
    Pair("Archive", Icons.Filled.Archive),
    Pair("Trash", Icons.Filled.Delete),
)

val configItem = mapOf("model" to modelList, "test" to bottomDrawerList)