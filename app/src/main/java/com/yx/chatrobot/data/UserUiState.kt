package com.yx.chatrobot.data

import com.yx.chatrobot.data.entity.User

data class UserUiState(
    val id: Int = 0,
    val name: String = "用户", // 昵称
    val account: String = "", // 账号，也是唯一的（和 id 的区别在于账号可以自己定义）
    val description: String = "这个人很懒，没有签名" // 个性说明 val id:Int
)

fun User.toUserUiState() = UserUiState(
    id = id,
    name = name,
    account = account,
    description = description
)

fun UserUiState.toUser() = User(
    id = id,
    name = name,
    account = account,
    description = description,
    password = ""
)