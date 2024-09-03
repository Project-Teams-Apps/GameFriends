package com.gamefriends.core.domain.usecase

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun loginUseCase(email: String, password: String): Flow<Resource<Token>>
}