package com.example.medicationreminder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.data.local.database.DatabaseConstants

@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(item: UserEntity)

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_USER}")
    abstract suspend fun getUser(): List<UserEntity?>

    @Query("DELETE FROM ${DatabaseConstants.TABLE_USER}")
    abstract suspend fun clearUser()
}