package com.gamefriends.core.data.source.remote

import com.gamefriends.core.data.source.remote.network.ApiResponse
import com.gamefriends.core.data.source.remote.network.ApiService
import com.gamefriends.core.data.source.remote.network.DTO
import com.gamefriends.core.data.source.remote.response.Data
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.qamar.curvedbottomnaviagtion.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class RemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun login(email: String, password: String): Flow<ApiResponse<LoginResponse>> =
        flow {
            try {

                val requestBody = DTO.loginBody(email, password)
                val response = apiService.login(requestBody)
                val data = response.token

                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }


            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }
}