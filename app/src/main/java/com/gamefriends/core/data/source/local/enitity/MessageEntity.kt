package com.gamefriends.core.data.source.local.enitity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "messages")
@Parcelize
data class MessageEntity(
    @PrimaryKey
    val messageId: String,

    val from_userId: String,

    val to_userId: String
): Parcelable