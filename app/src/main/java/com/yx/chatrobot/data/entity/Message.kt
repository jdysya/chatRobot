package com.yx.chatrobot.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // id
    val name: String, // 昵称
    val time: Long, // 时间戳（秒级别）
    val content: String, // 内容
    @ColumnInfo(name = "user_id")
    val userId: Int, // 对应的用户 Id
    val isSelf: Boolean // 是否为用户，否则为 AI 回复
)