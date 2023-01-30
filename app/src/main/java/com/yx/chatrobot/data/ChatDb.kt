package com.yx.chatrobot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yx.chatrobot.data.dao.MessageDao
import com.yx.chatrobot.data.dao.UserDao
import com.yx.chatrobot.data.entity.Message
import com.yx.chatrobot.data.entity.User

@Database(
    entities = [Message::class, User::class],
    version = 5,
    exportSchema = false
)
abstract class ChatDb : RoomDatabase() {
    abstract val messageDao: MessageDao
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var Instance: ChatDb? = null
        fun getDatabase(context: Context): ChatDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ChatDb::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}