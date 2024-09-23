package com.gamefriends.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileUser(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val bio: String = "",
    val gender: String = "",
    val gamePlayed: List<String> = emptyList(),
    val hobby: List<String> = emptyList(),
    val location: String = "",
    val profilePictureUrl: String = "",
): Parcelable
