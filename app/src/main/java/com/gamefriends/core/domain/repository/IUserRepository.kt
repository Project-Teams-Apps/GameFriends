package com.gamefriends.core.domain.repository

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun tokenProvider(): Flow<Token>

    fun login(email: String, password: String): Flow<Resource<Token>>
}