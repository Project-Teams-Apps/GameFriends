package com.gamefriends.core.domain.usecase

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun registerUseCase(email: String, name: String, password: String): Flow<Resource<RegisterResponse>>
}