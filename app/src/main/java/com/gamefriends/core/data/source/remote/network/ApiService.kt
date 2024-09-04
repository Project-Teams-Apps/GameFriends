package com.gamefriends.core.data.source.remote.network

import com.gamefriends.core.data.source.remote.DTO
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginBody: DTO.LoginBody
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerBody: DTO.RegisterBody
    ): RegisterResponse

    @POST("auth/verify/register")
    suspend fun verifyOtpRegister(
        @Body verifyOtpRegisterBody: DTO.VerifyOtpRegisterBody
    ): VerifyRegisterResponse

}