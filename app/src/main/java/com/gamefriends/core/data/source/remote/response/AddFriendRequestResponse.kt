package com.gamefriends.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AddFriendRequestResponse(

	@field:SerializedName("data")
	val data: DataFriendRequest? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataFriendRequest(

	@field:SerializedName("userRequestId")
	val userRequestId: String? = null,

	@field:SerializedName("userAcceptId")
	val userAcceptId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
