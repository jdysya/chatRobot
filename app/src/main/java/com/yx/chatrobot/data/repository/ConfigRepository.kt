package com.yx.chatrobot.data.repository

import com.yx.chatrobot.data.entity.Config
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun getConfigByUserIdStream(userId: Int): Flow<Config>
    suspend fun getConfigIdByUserId(userId: Int): Int
    suspend fun getConfigByUserId(userId: Int): Config
    suspend fun insert(config: Config)
    suspend fun update(config: Config)
}