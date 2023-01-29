package com.yx.chatrobot

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yx.chatrobot.data.Message
import com.yx.chatrobot.domain.ChatResponse
import com.yx.chatrobot.domain.RequestBody
import com.yx.chatrobot.network.ChatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainViewModel : ViewModel() {
    var fontSize = 20 // 当前页面的字体大小

    private lateinit var restaurantsCall: Call<ChatResponse>
    val messages = mutableStateListOf(
        Message(R.drawable.user_avatar, "自己", 1674467705, "生命的意义是什么？", true),
        Message(
            R.drawable.robot_avatar,
            "AI",
            1674468715,
            "人生的意义是活出自己的价值，不断发展自我，实现自我价值，追求自我价值的实现，为自己的人生赋予更多的意义，为自己的人生赋予更多的价值，为自己的人生赋予更多的意义，为自己的人生赋予更多的满足感，为自己的人生赋予更多的激情，为自己的人生赋予更多的梦想，为自己的人生赋予更多的希望，为自己的人生赋予更多的精彩。",
            false
        ),
        Message(R.drawable.user_avatar, "自己", 1674468922, "你能用c语言写一段代码吗？",true),
        Message(
            R.drawable.robot_avatar,
            "AI",
            1674468996,
            "#include <stdio.h>\n\nint main()\n{\n    int a = 10;\n    int b = 20;\n    int c = a + b;\n    printf(\"The sum of a and b is %d\\n\", c);\n    return 0;\n}",
            false
        )
    )
    val listState = LazyListState(0)// 记录聊天界面信息列表的位置状态

    fun getAiReply(content: String) {
        val requestBody = RequestBody("text-davinci-003", content, 0)
        restaurantsCall = ChatApi.retrofitService.getRestaurants(requestBody)
        restaurantsCall.enqueue(
            object : Callback<ChatResponse> {
                override fun onResponse(
                    call: Call<ChatResponse>,
                    response: Response<ChatResponse>
                ) {
                    Log.d("MYTEST", "获取信息成功")
                    response.body()?.choices?.get(0)?.let {
                        Log.d("MYTEST", it.text)

                        messages.add(
                            Message(
                                R.drawable.robot_avatar,
                                "AI",
                                Date().time / 1000,
                                it.text.trim(),
                                false
                            )
                        )

                    }
                    // 当从接口中获取到回复后，需要跳转到信息列表对应的位置
                    viewModelScope.launch {
                        listState.scrollToItem(messages.size - 1)
                    }

                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("MYTEST", "获取信息失败")
                }
            })
    }
}