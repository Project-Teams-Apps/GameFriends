package com.gamefriends.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "loginDataUser")
data class LoginEntity(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    var userId: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "token")
    var token: String
): Parcelable
