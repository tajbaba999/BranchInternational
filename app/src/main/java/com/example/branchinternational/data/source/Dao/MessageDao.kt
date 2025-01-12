package com.example.branchinternational.data.source.Dao

import androidx.room.Insert
import androidx.room.Query
import com.example.branchinternational.data.model.MessageEntity

interface MessageDao{

    @Insert
    suspend fun insertAll(message : List<MessageEntity>)

    @Query("SELECT * FROM messages")
    suspend fun getAllMessages() : List<MessageEntity>

    @Query("SELECT * FROM messages WHERE thread_id = :threadid")
    suspend fun getMessagesByThreadId(threadid : Int): List<MessageEntity>
}