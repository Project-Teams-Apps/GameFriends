package com.gamefriends.core.data.source.remote.network

import com.gamefriends.core.data.source.remote.DTO
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginBody: DTO.LoginBody
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerBody: DTO.RegisterBody
    ): RegisterResponse

    @POST("auth/register/verify")
    suspend fun verifyOtpRegister(
        @Body verifyOtpRegisterBody: DTO.VerifyOtpRegisterBody
    ): VerifyRegisterResponse

    @PATCH("profile/{id}")
    suspend fun gamePlayedBio(
        @Query("id") id: String,
        @Body gamePlayedBody: DTO.GamePlayedBody
    ): BioResponse

    @PATCH("profile/{id}")
    suspend fun genderBio(
        @Query("id") id: String,
        @Body genderBody: DTO.GenderBody
    ): BioResponse

    @POST("profile/{id}")
    suspend fun hobbyBio(
        @Query("id") id: String,
        @Body hobbyBody: DTO.HobbyBody
    ): BioResponse

    @POST("profile/{id}")
    suspend fun locationBio(
        @Path("id") id:String,
        @Body locationBody: DTO.LocationBody
    ): BioResponse

    @POST("profile/{id}")
    suspend fun bioBio(
        @Path("id") id: String,
        @Body bioBody: DTO.BioBody
    ): BioResponse

}