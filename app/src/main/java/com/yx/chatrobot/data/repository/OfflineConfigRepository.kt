package com.yx.chatrobot.data.repository

import com.yx.chatrobot.data.dao.ConfigDao
import com.yx.chatrobot.data.entity.Config
import kotlinx.coroutines.flow.Flow

class OfflineConfigRepository(
    private val configDao: ConfigDao
) : ConfigRepository {
    override fun getConfigByUserIdStream(userId: Int): Flow<Config> =
        configDao.getConfigByUserIdStream(userId)

    override suspend fun getConfigByUserId(userId: Int): Config {
        val res = configDao.getConfigByUserId(userId)
        return res ?: Config()
    }


    override suspend fun getConfigIdByUserId(userId: Int): Int {
        val res = configDao.getConfigIdByUserId(userId)
        return res ?: 0
    }

    override suspend fun insert(config: Config) = configDao.insert(config)
    override suspend fun update(config: Config) = configDao.update(config)
}