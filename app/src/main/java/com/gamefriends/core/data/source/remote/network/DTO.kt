package com.gamefriends.core.data.source.remote.network

object DTO  {

    data class loginBody (
        val email: String,
        val password: String
    )

}