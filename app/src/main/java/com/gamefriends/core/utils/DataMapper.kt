package com.gamefriends.core.utils

import com.gamefriends.core.data.source.local.entity.LoginEntity
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.domain.model.LoginUserData
import java.io.DataInput

object DataMapper {

    fun mapResponseToEntities(input: LoginResponse): LoginEntity {
        val loginUserdata = LoginEntity(
            userId = input.data.userId,
            email = input.data.email,
            name = input.data.name,
            token = input.token
        )

        return loginUserdata
    }

    fun mapEntitiesToDomain(input: LoginEntity): LoginUserData  =
        LoginUserData(
            userId = input.userId,
            email = input.email,
            name = input.name,
            token = input.token
        )


    fun mapDomainToEntity(input: LoginUserData) = LoginEntity(
        userId = input.userId,
        email = input.email,
        name = input.name,
        token = input.token
    )

}