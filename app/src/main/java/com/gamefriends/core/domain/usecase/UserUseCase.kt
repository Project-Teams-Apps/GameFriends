package com.gamefriends.core.domain.usecase

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import com.gamefriends.core.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getToken(): Flow<Token>
    fun loginUseCase(email: String, password: String): Flow<Resource<Token>>

    fun registerUseCase(email: String, name: String, password: String): Flow<Resource<RegisterResponse>>

    fun verifyOtpUseCase(email: String, otp: String): Flow<Resource<Token>>

    fun gamePlayedBio(gamePlayed: List<String>): Flow<Resource<BioResponse>>

    fun genderBio(gendrBioString: String): Flow<Resource<BioResponse>>

    fun hobbyBio(hobby: List<String>): Flow<Resource<BioResponse>>
}