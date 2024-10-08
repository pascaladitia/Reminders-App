package com.example.medicationreminder.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medicationreminder.data.local.database.DatabaseConstants
import com.example.medicationreminder.data.local.model.TaskEntity

@Dao
abstract class TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTask(item: TaskEntity)

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_TASK}")
    abstract suspend fun getTask(): List<TaskEntity?>

    @Delete
    abstract suspend fun deleteTask(item: TaskEntity) : Int

    @Query("DELETE FROM ${DatabaseConstants.TABLE_TASK}")
    abstract suspend fun clearTask()
}