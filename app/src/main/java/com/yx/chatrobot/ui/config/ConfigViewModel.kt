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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ConfigViewModel(
    savedStateHandle: SavedStateHandle,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val configRepository: ConfigRepository,
) : ViewModel() {
    private val userId: Int = checkNotNull(savedStateHandle["userId"])
    var isListShow = mutableStateOf(false)
    var selectToShow = mutableStateOf("")
    var currentInputValue by mutableStateOf("")
    var drawerIndex = mutableStateOf(0)
    var configUiState by mutableStateOf(ConfigUiState())
        private set

    init {
        viewModelScope.launch {
            configUiState = configRepository.getConfigByUserIdStream(userId)
                .filterNotNull()
                .first()
                .toConfigUiState()
        }
    }

    fun updateState(flag: Boolean, name: String) {
        isListShow.value = flag
        selectToShow.value = name
        when (name) {
            "maxTokens" -> currentInputValue = configUiState.maxTokens.toString()
            "temperature" -> currentInputValue = configUiState.temperature.toString()
            "topP" -> currentInputValue = configUiState.topP.toString()
            "stop" -> currentInputValue = configUiState.stop
        }
        // 根据当前的选项，匹配对应列表中的索引值
        configItem[name]?.forEachIndexed { index, pair ->
            when (name) {
                "model" -> if (pair.first == configUiState.model) {
                    drawerIndex.value = index
                }
            }
        }
    }

    fun updateValue(value: String) {
        try {
            when (selectToShow.value) { // 根据当前选择的值进行更新状态
                "model" -> configUiState = configUiState.copy(model = value)
                "maxTokens" -> configUiState = configUiState.copy(maxTokens = value.toInt())
                "temperature" -> configUiState = configUiState.copy(temperature = value.toDouble())
                "topP" -> configUiState = configUiState.copy(topP = value.toDouble())
                "stop" -> configUiState = configUiState.copy(stop = value)
            }
        } catch (e: NumberFormatException) {
            Log.e("mytest", "输入内容与对应数值不匹配:${e.message}")
        }

    }

    fun saveConfig() {
        viewModelScope.launch {
            //先判断用户在数据库中有没有配置记录，
            // 若没有，则插入
            // 否则，更新用户的配置项
            val flag = async { configRepository.getConfigIdByUserId(userId) }
            if (flag.await() != 0) {
                configRepository.update(configUiState.toConfig(flag.await(), userId))
            } else {
                configRepository.insert(configUiState.toConfig(userId = userId))
            }
        }
    }


    //    val configState: StateFlow<String> =
//        userPreferencesRepository.config
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = ""
//            )
//
//    fun saveConfig() {
//        viewModelScope.launch {
//            userPreferencesRepository.saveUserPreference()
//        }
//    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}