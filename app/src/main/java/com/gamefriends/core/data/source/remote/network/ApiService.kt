package com.gamefriends.core.data.source.remote.network

import com.gamefriends.core.data.source.remote.DTO
import com.gamefriends.core.data.source.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST
    suspend fun login(
        @Body loginBody: DTO.loginBody
    ): LoginResponse


}