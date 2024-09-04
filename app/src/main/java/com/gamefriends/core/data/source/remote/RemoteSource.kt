package com.gamefriends.core.data.source.remote

import com.gamefriends.core.data.source.remote.network.ApiResponse
import com.gamefriends.core.data.source.remote.network.ApiService
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun login(email: String, password: String):Flow<ApiResponse<LoginResponse>> =
        flow {
            try {
                val requestBody = DTO.LoginBody(email, password)
                val response = apiService.login(requestBody)


                if (response.token?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)


    suspend fun register(email: String, name: String, password: String): Flow<ApiResponse<RegisterResponse>> =
        flow {
            try {
                val requestBody = DTO.RegisterBody(email, name, password)
                val response = apiService.register(requestBody)

                if (response.data?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }
}