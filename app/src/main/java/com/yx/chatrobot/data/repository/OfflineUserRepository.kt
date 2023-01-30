package com.yx.chatrobot.data.repository

import com.yx.chatrobot.data.dao.UserDao
import com.yx.chatrobot.data.entity.User
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {
    override fun getUserById(id: Int): Flow<User> {
        return userDao.getUserById(id)
    }
    override suspend fun insert(user: User) = userDao.insert(user)
    override suspend fun getUserByAccount(account: String, password: String): User {
        val res = userDao.getUserByAccount(account, password)
        // 如果查询不到对应的，则返回默认值
        // 将返回的密码进行加密处理
        return (res ?: User()).copy(password = "******")
    }

    override suspend fun isExistSameAccount(account: String): Boolean {
        val res = userDao.isExistSameAccount(account)
        return res == 1
    }

}