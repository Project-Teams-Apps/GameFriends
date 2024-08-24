package com.gamefriends.core.domain.repository

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.domain.model.LoginUserData
import kotlinx.coroutines.flow.Flow

interface IUserRepository  {

    fun getAllLoginUserData(email: String, password: String): Flow<Resource<LoginUserData>>

    fun getTokenUser(): Flow<LoginUserData>

}