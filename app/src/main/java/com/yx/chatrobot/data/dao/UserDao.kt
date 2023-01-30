package com.yx.chatrobot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yx.chatrobot.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from user where id = :id")
    fun getUserById(id: Int): Flow<User>

    @Query("select * from user where account = :account and password =:password")
    suspend fun getUserByAccount(account: String, password: String): User

    @Query("select count(*) from user where account = :account")
    suspend fun isExistSameAccount(account: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
}