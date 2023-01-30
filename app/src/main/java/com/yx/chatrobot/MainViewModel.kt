package com.yx.chatrobot

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yx.chatrobot.data.*
import com.yx.chatrobot.data.entity.Message
import com.yx.chatrobot.data.entity.User
import com.yx.chatrobot.data.repository.MessageRepository
import com.yx.chatrobot.data.repository.UserRepository
import com.yx.chatrobot.domain.ChatResponse
import com.yx.chatrobot.domain.RequestBody
import com.yx.chatrobot.network.ChatApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainViewModel(
    savedStateHandle: SavedStateHandle,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val userId: Int = checkNotNull(savedStateHandle["userId"])
    var fontSize = 20 // 当前页面的字体大小
    private lateinit var restaurantsCall: Call<ChatResponse>
    private val userName: String = "自己"
    private val aiName: String = "AI"
    val userState: StateFlow<UserUiState> =
        userRepository.getUserById(userId)
            .filterNotNull()
            .map {
                it.toUserUiState()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserUiState()
            )
    val chatListState: StateFlow<ChatUiState> =
        messageRepository.getMessagesStreamByUserId(userId)
            .filterNotNull()
            .map { ChatUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ChatUiState()
            )
    val listState = LazyListState(0)// 记录聊天界面信息列表的位置状态


    fun getAiReply(content: String) {
        updateMessageUiState(content, true) // 将用户的输入进行记录
        val requestBody = RequestBody("text-davinci-003", content, 0)
        restaurantsCall = ChatApi.retrofitService.getRestaurants(requestBody)
        restaurantsCall.enqueue(
            object : Callback<ChatResponse> {
                override fun onResponse(
                    call: Call<ChatResponse>,
                    response: Response<ChatResponse>
                ) {
                    response.body()?.choices?.get(0)?.let {
                        updateMessageUiState(it.text.trim(), false)
                    }
                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("MYTEST", "获取信息失败")
                }
            })
    }


    fun updateMessageUiState(result: String, isSelf: Boolean) {
        val tmp = MessageUiState(
            name = if (isSelf) userState.value.name else aiName,
            time = Date().time / 1000,
            content = result,
            isSelf = isSelf,
        )
        viewModelScope.launch {
            messageRepository.insertMessage(tmp.toMessage(userState.value.id))
            delay(100)
            listState.scrollToItem(chatListState.value.chatList.size - 1)
        }
    }

    data class ChatUiState(val chatList: List<Message> = listOf())
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}