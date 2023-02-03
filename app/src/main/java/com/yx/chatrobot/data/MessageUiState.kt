package com.yx.chatrobot.data

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.yx.chatrobot.R
import com.yx.chatrobot.data.entity.Message
import java.text.SimpleDateFormat
import java.time.LocalDate

data class MessageUiState(
    val id: Int = 0,
    val name: String = "", // 昵称
    val time: Long = 1674973683, // 时间(时间戳形式，以秒计时)
    val dateStr: String = "2023-2-3 11:42",
    val content: String = "", // 内容
    val isSelf: Boolean = false // 是否为本人（否则为 AI 回复）
)

fun MessageUiState.toMessage(userId: Int): Message = Message(
    id = id,
    name = name,
    time = time,
    content = content,
    userId = userId,
    isSelf = isSelf
)

@RequiresApi(Build.VERSION_CODES.O)
fun Message.toMessageUiState(): MessageUiState = MessageUiState(
    id = id,
    name = name,
    time = time,
    dateStr = dateToStrFriendly(time),
    content = content,
    isSelf = isSelf
)


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
fun dateToStrFriendly(timeStamp: Long): String {
    val timeIns = LocalDate.now()
    val yearNow = timeIns.year
    val year = (timeStamp / 3600 / 24 / 365 + 1970).toInt()
    var res = ""
    if (year == yearNow) {
        // 如果消息时间还在今年，则不显示年份
        res = SimpleDateFormat("MM-dd HH:mm").format(timeStamp * 1000)
    } else {
        // 否则显示完整年份
        res = SimpleDateFormat("yyyy-MM-dd HH:mm").format(timeStamp * 1000)
    }
    return res
}







