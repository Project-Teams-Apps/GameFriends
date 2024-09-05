package com.gamefriends.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    var userId: String,
    var token: String,
    var isLogin: Boolean = false
): Parcelable
