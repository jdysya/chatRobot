package com.yx.chatrobot.network

import com.yx.chatrobot.domain.ChatResponse
import com.yx.chatrobot.domain.RequestBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


private const val BASE_URL =
    "https://api.openai.com/v1/"

var httpBuilder = OkHttpClient.Builder()
// 设置超时，超过 60s 视为超时
var client = httpBuilder.readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(client)
    .build()




interface ChatApiService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:Bearer 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"
    )
    @POST("completions")
    fun getRestaurants(@Body requestData: RequestBody): Call<ChatResponse>
}

object ChatApi {
    val retrofitService: ChatApiService by lazy {
        retrofit.create(ChatApiService::class.java)
    }
}