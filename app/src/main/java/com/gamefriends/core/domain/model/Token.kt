package com.gamefriends.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    var token: String,
    var isLogin: Boolean = false
): Parcelable
