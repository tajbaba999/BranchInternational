package com.example.branchinternational.data.model

import java.sql.Timestamp

data class Message(
    val id : Int,
    val thread_id : Int,
    val user_id : String,
    val body : String,
    val timestamp: String,
    val agent_id : String? = null
)
