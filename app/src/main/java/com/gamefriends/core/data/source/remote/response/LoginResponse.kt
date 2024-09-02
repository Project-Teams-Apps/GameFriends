package com.gamefriends.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
	val data: Data? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("token")
	val token: String? = null
)

data class Data(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
