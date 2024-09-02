package com.gamefriends.core.data.source.remote

object DTO {

    data class loginBody(
        var email: String,
        var password: String
    )
}