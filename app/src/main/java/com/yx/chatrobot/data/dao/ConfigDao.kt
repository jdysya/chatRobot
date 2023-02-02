package com.yx.chatrobot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yx.chatrobot.data.entity.Config
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigDao {
    @Query("select * from config where user_id = :userId")
    fun getConfigByUserIdStream(userId: Int): Flow<Config>

    @Query("select * from config where user_id = :userId")
    suspend fun getConfigByUserId(userId: Int): Config

    @Query("select id from config where user_id= :userId")
    suspend fun getConfigIdByUserId(userId: Int): Int

    @Update
    suspend fun update(config: Config)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(config: Config)
}