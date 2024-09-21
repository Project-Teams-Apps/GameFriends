package com.gamefriends.core.data.source.local.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remoteEntity")
data class RemoteEntity(
    @PrimaryKey
    val id: String,

    val prevKey: Int?,
    val nextKey: Int?
)
