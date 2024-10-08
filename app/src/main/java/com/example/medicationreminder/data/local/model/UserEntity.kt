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
    val id: Int? = null,

    @field:SerializedName("nim")
    var nim: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("password")
    var password: String? = null
): Parcelable