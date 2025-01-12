package com.example.branchinternational.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.branchinternational.data.model.MessageEntity
import com.example.branchinternational.data.source.Dao.MessageDao


@Database(entities = [MessageEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao
}