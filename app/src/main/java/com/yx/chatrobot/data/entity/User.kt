package com.yx.chatrobot.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "游客", // 昵称
    val account: String = "1234", // 账号，也是唯一的（和 id 的区别在于账号可以自己定义）
    val password: String = "", // 密码，存入数据库前先进行 MD5加密
    val description: String = "这个人很懒，什么都没有" // 个性说明
)