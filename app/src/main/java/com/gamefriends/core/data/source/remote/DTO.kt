package com.gamefriends.core.data.source.remote

object DTO {

    data class LoginBody(
        var email: String,
        var password: String
    )

    data class RegisterBody(
        var email: String,
        var name: String,
        var password: String
    )
}