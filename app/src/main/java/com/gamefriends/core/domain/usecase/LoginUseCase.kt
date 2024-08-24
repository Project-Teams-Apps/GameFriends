package com.gamefriends.core.domain.usecase

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.domain.model.LoginUserData
import kotlinx.coroutines.flow.Flow


interface LoginUseCase {
    fun getAllLoginUserData(email: String, password: String): Flow<Resource<LoginUserData>>

}