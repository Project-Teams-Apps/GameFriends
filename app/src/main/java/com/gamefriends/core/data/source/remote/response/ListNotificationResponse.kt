package com.gamefriends.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListNotificationResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("userRequestId")
	val userRequestId: String? = null,

	@field:SerializedName("userRequest")
	val userRequest: UserRequest? = null,

	@field:SerializedName("userAcceptId")
	val userAcceptId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class UserRequest(

	@field:SerializedName("bioUser")
	val bioUser: BioUserProfiles? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class BioUserProfiles(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("profilePicureUrl")
	val profilePicureUrl: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("gamePlayed")
	val gamePlayed: List<String?>? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("hobby")
	val hobby: List<String?>? = null
)
