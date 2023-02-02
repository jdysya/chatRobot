package com.yx.chatrobot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yx.chatrobot.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from user where id = :id")
    fun getUserById(id: Int): Flow<User>

    @Query("update user set name = :newName where id = :id")
    suspend fun updateNameById(id: Int, newName: String)

    @Query("update user set description = :newVal where id = :id")
    suspend fun updateDesById(id: Int, newVal: String)

    @Query("select * from user where account = :account and password =:password")
    suspend fun getUserByAccount(account: String, password: String): User

    @Query("select count(*) from user where account = :account")
    suspend fun isExistSameAccount(account: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
}