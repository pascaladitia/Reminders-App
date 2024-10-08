package com.example.medicationreminder.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "task")
data class TaskEntity(
    @ColumnInfo(name = "title")
    val title:String? = null,

    @ColumnInfo(name = "description")
    val description:String? = null,

    @ColumnInfo(name = "date")
    val date: String? = null,

    @ColumnInfo(name = "time")
    val time: String? = null,

    @ColumnInfo(name = "is_done")
    val isDone: Boolean? = null,

    @ColumnInfo(name = "priority")
    val priority : String? = "",

    @ColumnInfo(name = "alarm_id")
    val alarmId : Long = 0,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) :Parcelable