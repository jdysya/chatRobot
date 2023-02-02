package com.yx.chatrobot.data.repository

import com.yx.chatrobot.data.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserById(id: Int): Flow<User>
    suspend fun updateNameById(id: Int,name:String)
    suspend fun updateDesById(id: Int, newVal: String)
    suspend fun getUserByAccount(account: String, password: String): User
    suspend fun insert(user: User)
    suspend fun isExistSameAccount(account: String): Boolean
}