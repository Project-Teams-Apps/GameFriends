package com.gamefriends.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginUserData(

    val userId: String,
    val email: String,
    val name: String,
    val token: String

): Parcelable
