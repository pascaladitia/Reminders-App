package com.example.medicationreminder.domain.repository

import com.example.medicationreminder.data.local.LocalDataSource
import com.example.medicationreminder.data.local.model.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class LocalRepository(
    private val localDataSource: LocalDataSource
): ILocalRepository {

    /** User */
    override suspend fun getUser(): Flow<List<UserEntity?>> {
        return flowOf(localDataSource.getUser())
    }

    override suspend fun addUser(item: UserEntity): Flow<Unit> {
        return flowOf(localDataSource.addUser(item))
    }

    override suspend fun deleteUser(): Flow<Unit> {
        return flowOf(localDataSource.deleteUser())
    }
}