package com.gamefriends.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListChatResponse(

	@field:SerializedName("data")
	val data: Datachats? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class GetListMessageItem(

	@field:SerializedName("senderName")
	val senderName: String? = null,

	@field:SerializedName("receiverName")
	val receiverName: String? = null,

	@field:SerializedName("lastMessage")
	val lastMessage: String? = null,

	@field:SerializedName("receiverProfilePictureUrl")
	val receiverProfilePictureUrl: String? = null,

	@field:SerializedName("lastSendDateTime")
	val lastSendDateTime: String? = null,

	@field:SerializedName("senderProfilePictureUrl")
	val senderProfilePictureUrl: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)

data class Datachats(

	@field:SerializedName("listUserChat")
	val listUserChat: ListUserChat? = null
)

data class ListUserChat(

	@field:SerializedName("getListMessage")
	val getListMessage: List<GetListMessageItem?>? = null
)
