package com.gamefriends.core.data.source.local.enitity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "feedUser")
@Parcelize
data class FeedUserEntity(
    @PrimaryKey
    val userId: String,

    val name: String,

    val bio: String,

    val gender: String,

    val gamePlayed: List<String?>,

    val hobby: List<String?>,

    val location: String,

    @ColumnInfo("profilePictureUrl")
    val profilePictureUrl: String,
): Parcelable
