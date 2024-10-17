package com.gamefriends.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ChatHistoryResponse(

	@field:SerializedName("data")
	val data: DataHistory? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataHistory(

	@field:SerializedName("chatHistory")
	val chatHistory: ChatHistory? = null
)

data class ToUser(

	@field:SerializedName("profilePictureUrl")
	val profilePictureUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class GetHistoryItem(

	@field:SerializedName("toUser")
	val toUser: ToUser? = null,

	@field:SerializedName("fromUser")
	val fromUser: FromUser? = null,

	@field:SerializedName("send_dateTime")
	val sendDateTime: String? = null
)

data class ChatHistory(

	@field:SerializedName("getHistory")
	val getHistory: List<GetHistoryItem?>? = null
)

data class FromUser(

	@field:SerializedName("profilePictureUrl")
	val profilePictureUrl: String? = null,

	@field:SerializedName("messageText")
	val messageText: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)
