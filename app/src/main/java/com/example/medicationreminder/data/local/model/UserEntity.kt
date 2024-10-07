package com.example.medicationreminder.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.example.medicationreminder.data.local.database.DatabaseConstants
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = DatabaseConstants.TABLE_USER)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val no: Int,

    @field:SerializedName("collectorID")
    var collectorID: Long? = null,

    @field:SerializedName("code")
    var code: String? = null,

    @field:SerializedName("name")
    var desc: String? = null,

    @field:SerializedName("is_active")
    var isActive: Int? = 0,

    @field:SerializedName("tboption")
    var tboption: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("id")
    val id: Long? = null,
): Parcelable