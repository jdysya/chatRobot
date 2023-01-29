package com.yx.chatrobot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yx.chatrobot.data.entity.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(message: Message)

    @Query("SELECT * from message WHERE id = :id")
    fun getMessage(id: Int): Flow<Message>

    @Query("Select * from  message where user_id = :userId order by time asc")
    fun getAllMessagesByUserId(userId: Int): Flow<List<Message>>

}