package com.example.medicationreminder.domain.repository

import com.example.medicationreminder.data.local.model.TaskEntity
import com.example.medicationreminder.data.local.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface ILocalRepository {
    suspend fun getUser(): Flow<List<UserEntity?>>
    suspend fun addUser(item: UserEntity): Flow<Unit>
    suspend fun deleteUser(): Flow<Unit>

    suspend fun getTask(): Flow<List<TaskEntity?>>
    suspend fun addTask(item: TaskEntity): Flow<Unit>
    suspend fun deleteTask(item: TaskEntity): Flow<Unit>
    suspend fun clearTask(): Flow<Unit>
}