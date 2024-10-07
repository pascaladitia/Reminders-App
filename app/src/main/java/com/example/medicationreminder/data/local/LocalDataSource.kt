package com.example.medicationreminder.data.local

import com.example.medicationreminder.data.local.database.AppDatabase
import com.example.medicationreminder.data.local.model.UserEntity

class LocalDataSource(private val database: AppDatabase) {
    /** User */
    suspend fun addUser(entity: UserEntity) {
        database.userDao().insertUser(entity)
    }

    suspend fun deleteUser() {
        database.userDao().clearUser()
    }

    suspend fun getUser(): List<UserEntity?> {
        return database.userDao().getUser()
    }
}