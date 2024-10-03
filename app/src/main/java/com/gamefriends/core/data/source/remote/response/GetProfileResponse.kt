package com.gamefriends.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(

	@field:SerializedName("data")
	val data: DataProfile? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class BioUserProfile(

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

data class DataProfile(

	@field:SerializedName("bioUser")
	val bioUser: BioUserProfile? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
