package com.example.medicationreminder.data.local

import com.example.medicationreminder.data.local.database.AppDatabase
import com.example.medicationreminder.data.local.model.TaskEntity
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
    /** Task */
    suspend fun addTask(entity: TaskEntity) {
        database.taskDao().insertTask(entity)
    }

    suspend fun getTask(): List<TaskEntity?> {
        return database.taskDao().getTask()
    }

    suspend fun deleteTask(item: TaskEntity) {
        database.taskDao().deleteTask(item)
    }

    suspend fun clearTask() {
        database.taskDao().clearTask()
    }
}