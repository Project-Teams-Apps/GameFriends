package com.gamefriends.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedUser(
    val userId: String,
    val name: String,
    val bio: String,
    val gender: String,
    val gamePlayed: List<String>,
    val hobby: List<String>,
    val location: String,
    val profilePictureUrl: String,
): Parcelable
