package com.gamefriends.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponse(

	@field:SerializedName("pagination")
	val pagination: Pagination? = null,

	@field:SerializedName("data")
	val data: DataList? = null
)

data class ListItem(

	@field:SerializedName("bioUser")
	val bioUser: BioUser? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class BioUser(

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

	@field:SerializedName("hobby")
	val hobby: List<String?>? = null
)

data class Pagination(

	@field:SerializedName("totalRecords")
	val totalRecords: Int? = null,

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("pageSize")
	val pageSize: Int? = null,

	@field:SerializedName("currentPages")
	val currentPages: Int? = null
)

data class DataList(

	@field:SerializedName("list")
	val list: List<ListItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)
