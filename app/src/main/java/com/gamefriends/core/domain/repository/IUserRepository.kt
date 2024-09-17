package com.gamefriends.core.domain.repository

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import com.gamefriends.core.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun tokenProvider(): Flow<Token>

    fun login(email: String, password: String): Flow<Resource<Token>>

    fun register(email: String, name: String, password: String): Flow<Resource<RegisterResponse>>

    fun verifyOtpRegister(email: String, otp: String): Flow<Resource<Token>>

    fun gamePlayedBio(gamePlayed: List<String>): Flow<Resource<BioResponse>>

    fun genderBio(genderBioString: String): Flow<Resource<BioResponse>>

    fun hobbyBio(hobby: List<String>): Flow<Resource<BioResponse>>

    fun bioUser(bio: String): Flow<Resource<BioResponse>>

    fun locationUser(location: String): Flow<Resource<BioResponse>>
}