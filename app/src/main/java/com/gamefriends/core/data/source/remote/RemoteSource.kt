package com.gamefriends.core.data.source.remote

import com.gamefriends.core.data.source.remote.network.ApiResponse
import com.gamefriends.core.data.source.remote.network.ApiService
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import com.gamefriends.core.domain.model.Token
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
        }.flowOn(Dispatchers.IO)


    suspend fun verifyOtpRegister(email: String, otp: String): Flow<ApiResponse<VerifyRegisterResponse>> =
        flow {
            try {
                val requestBody = DTO.VerifyOtpRegisterBody(email, otp)
                val response = apiService.verifyOtpRegister(requestBody)

                if (response.token?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun gamePlayedBio(userId: String, gamePlayedString: List<String>): Flow<ApiResponse<BioResponse>> =
        flow {
            try {
                val requestBody = DTO.GamePlayedBody(gamePlayedString)
                val response = apiService.gamePlayedBio(userId, requestBody)

                if (response.data?.gamePlayed?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)


    suspend fun genderBio(userId: String, gender: String): Flow<ApiResponse<BioResponse>> =
        flow {
            try {
                val requestBody = DTO.GenderBody(gender)
                val response = apiService.genderBio(userId, requestBody)

                if (response.data?.bio?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)


    suspend fun hobbyBio(userId: String, hobby: List<String>): Flow<ApiResponse<BioResponse>> =
        flow {
            try {
                val requestBody = DTO.HobbyBody(hobby)
                val response = apiService.hobbyBio(userId, requestBody)

                if (response.data?.bio?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun bioUser(userId: String, bio: String): Flow<ApiResponse<BioResponse>> =
        flow {
            try {
                val requestBody = DTO.BioBody(bio)
                val response = apiService.bioBio(userId, requestBody)

                if (response.data?.bio?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun locationUser(userId: String, location: String): Flow<ApiResponse<BioResponse>> =
        flow {
            try {
                val requestBody = DTO.LocationBody(location)
                val response = apiService.locationBio(userId, requestBody)

                if (response.data?.location?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
}