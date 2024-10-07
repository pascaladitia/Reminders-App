package com.example.medicationreminder.domain.usecase

import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.domain.repository.ILocalRepository
import kotlinx.coroutines.flow.Flow

class LocalUC (private val repository: ILocalRepository) {

    /** User */
    suspend fun getUser(): Flow<List<UserEntity?>> {
        return repository.getUser()
    }

    suspend fun addUser(item: UserEntity): Flow<Unit> {
        return repository.addUser(item)
    }

    suspend fun deleteUser(): Flow<Unit> {
        return repository.deleteUser()
    }
}