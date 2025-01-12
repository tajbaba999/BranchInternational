package com.example.branchinternational.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
   @PrimaryKey val id : Int,
   @ColumnInfo(name = "thread_id") val thread_id: Int,
   @ColumnInfo(name = "name") val user_id: String,
   @ColumnInfo(name = "body") val body: String,
   @ColumnInfo(name = "timestamp" ) val timestamp: String,
   @ColumnInfo(name = "agent_id") val agent_id: String?
)