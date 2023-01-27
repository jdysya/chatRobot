package com.yx.chatrobot

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.yx.chatrobot.data.Message
import com.yx.chatrobot.domain.ChatResponse
import com.yx.chatrobot.domain.RequestBody
import com.yx.chatrobot.network.ChatApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainViewModel : ViewModel() {
    var fontSize = 20 // 当前页面的字体大小

    //    private var restInterface: RestaurantsApiService
    private lateinit var restaurantsCall: Call<ChatResponse>

    //    val messageState = mutableStateOf(emptyList<ChatResponse>())
    val messages = mutableStateListOf(
        Message(R.drawable.user_avatar, "自己", 1674467705, "生命的意义是什么？"),
        Message(
            R.drawable.robot_avatar,
            "AI",
            1674468715,
            "人生的意义是活出自己的价值，不断发展自我，实现自我价值，追求自我价值的实现，为自己的人生赋予更多的意义，为自己的人生赋予更多的价值，为自己的人生赋予更多的意义，为自己的人生赋予更多的满足感，为自己的人生赋予更多的激情，为自己的人生赋予更多的梦想，为自己的人生赋予更多的希望，为自己的人生赋予更多的精彩。"
        ),
        Message(R.drawable.user_avatar, "自己", 1674468922, "你能用c语言写一段代码吗？"),
        Message(
            R.drawable.robot_avatar,
            "AI",
            1674468996,
            "#include <stdio.h>\n\nint main()\n{\n    int a = 10;\n    int b = 20;\n    int c = a + b;\n    printf(\"The sum of a and b is %d\\n\", c);\n    return 0;\n}"
        )
    )


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
                                it.text
                            )
                        )
                    }

                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("MYTEST", "获取信息失败")
                }
            })
    }
}