package com.gamefriends.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class ListChatUser(
    val userId: String,
    val lastMessage: String,
    val lastSendDateTime: String,
    val senderName: String,
    val senderProfilePictureUrl: String,
    val receiverName: String,
    val receiverProfilePictureUrl: String
): Parcelable