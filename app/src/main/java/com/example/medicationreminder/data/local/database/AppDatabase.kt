package com.example.medicationreminder.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.medicationreminder.data.local.dao.UserDao
import com.example.medicationreminder.data.local.model.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}