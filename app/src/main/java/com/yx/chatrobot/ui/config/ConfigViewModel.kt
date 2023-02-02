package com.yx.chatrobot.ui.config

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yx.chatrobot.MainViewModel
import com.yx.chatrobot.data.*
import com.yx.chatrobot.data.entity.Config
import com.yx.chatrobot.data.repository.ConfigRepository
import com.yx.chatrobot.data.repository.OfflineConfigRepository
import com.yx.chatrobot.data.repository.UserPreferencesRepository
import com.yx.chatrobot.data.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ConfigViewModel(
    savedStateHandle: SavedStateHandle,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val configRepository: ConfigRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val userId: Int = checkNotNull(savedStateHandle["userId"])
    var isListShow = mutableStateOf(false)
    var selectToShow = mutableStateOf("")
    var currentInputValue by mutableStateOf("")
    var drawerIndex = mutableStateOf(0)
    var configUiState by mutableStateOf(ConfigUiState())
        private set
    val themeState: StateFlow<Boolean> =
        userPreferencesRepository.themeConfig
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    val fontState: StateFlow<String> =
        userPreferencesRepository.fontConfig
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "中"
            )

    var userUiState by mutableStateOf(UserUiState())
        private set

    init {
        viewModelScope.launch {
            configUiState = configRepository.getConfigByUserIdStream(userId)
                .filterNotNull()
                .first()
                .toConfigUiState()
            userUiState = userRepository.getUserById(userId)
                .filterNotNull()
                .first()
                .toUserUiState()
        }
    }

    fun updateState(flag: Boolean, name: String) {
        isListShow.value = flag
        selectToShow.value = name
        when (name) {
            "maxTokens" -> currentInputValue = configUiState.maxTokens.toString()
            "temperature" -> currentInputValue = configUiState.temperature.toString()
            "topP" -> currentInputValue = configUiState.topP.toString()
            "frequencyPenalty" -> currentInputValue = configUiState.frequency_penalty.toString()
            "presencePenalty" -> currentInputValue = configUiState.presence_penalty.toString()
            "robotName" -> currentInputValue = configUiState.robotName
            "userName" -> currentInputValue = userUiState.name
            "description" -> currentInputValue = userUiState.description
        }
        // 根据当前的选项，匹配对应列表中的索引值
        configItem[name]?.forEachIndexed { index, pair ->
            when (name) {
                "model" -> if (pair.first == configUiState.model) drawerIndex.value = index
                "fontSize" -> if (pair.first == fontState.value) drawerIndex.value = index
            }
        }
    }

    fun updateValue(value: String) {
        try {
            when (selectToShow.value) { // 根据当前选择的值进行更新状态
                "model" -> configUiState = configUiState.copy(model = value)
                "fontSize" -> saveFontState(value)
                "maxTokens" -> configUiState = configUiState.copy(maxTokens = value.toInt())
                "temperature" -> configUiState = configUiState.copy(temperature = value.toDouble())
                "topP" -> configUiState = configUiState.copy(topP = value.toDouble())
                "frequencyPenalty" -> configUiState =
                    configUiState.copy(frequency_penalty = value.toDouble())
                "presencePenalty" -> configUiState =
                    configUiState.copy(presence_penalty = value.toDouble())
                "robotName" -> configUiState = configUiState.copy(robotName = value)
                "userName" -> userUiState = userUiState.copy(name = value)
                "description" -> userUiState = userUiState.copy(description = value)
//                "stop" -> configUiState = configUiState.copy(stop = value)
            }
        } catch (e: NumberFormatException) {
            Log.e("mytest", "输入内容与对应数值不匹配:${e.message}")
        }

    }

    fun saveConfig(
        onSaveClick: () -> Unit
    ) {
        viewModelScope.launch {
            //先判断用户在数据库中有没有配置记录，
            // 若没有，则插入
            // 否则，更新用户的配置项
            try {
                val flag = async { configRepository.getConfigIdByUserId(userId) }
                if (flag.await() != 0) {
                    configRepository.update(configUiState.toConfig(flag.await(), userId))
                } else {
                    configRepository.insert(configUiState.toConfig(userId = userId))
                }
                userRepository.updateNameById(userId, userUiState.name)// 保存用户昵称
                userRepository.updateDesById(userId, userUiState.description) // 保存用户签名
                onSaveClick()
            } catch (e: java.lang.Exception) {
                Log.e("mytest", "出现错误:${e.message}")
            }
        }
    }


    fun saveThemeState(value: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreference(value)
        }
    }

    fun saveFontState(value: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserFontPreference(value)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}