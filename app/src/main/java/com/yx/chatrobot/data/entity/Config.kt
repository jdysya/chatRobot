package com.yx.chatrobot.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
data class Config(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "robot_name")
    val robotName: String = "AI",
    val model: String ="text-davinci-003",
    @ColumnInfo(name = "max_tokens")
    val maxTokens: Int = 60,
    val temperature: Double = 0.5,
    @ColumnInfo(name = "top_p")
    val topP: Double = 1.0,
    val stop: String = "",
    @ColumnInfo(name = "user_id")
    val userId: Int = 1314
)