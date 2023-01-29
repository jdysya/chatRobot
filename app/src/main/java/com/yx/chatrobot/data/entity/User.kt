package com.yx.chatrobot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val password: String, // 密码，存入数据库前先进行 MD5加密
    val avatar: Int, // 头像
    val description: String // 个性说明
)