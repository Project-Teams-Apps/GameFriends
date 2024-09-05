package com.gamefriends.core.data.source

import com.gamefriends.core.data.source.preferences.TokenPreferences
import com.gamefriends.core.data.source.remote.RemoteSource
import com.gamefriends.core.data.source.remote.network.ApiResponse
import com.gamefriends.core.data.source.remote.response.ErrorResponse
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import com.gamefriends.core.domain.model.Token
import com.gamefriends.core.domain.repository.IUserRepository
import com.gamefriends.core.utils.AppExecutors
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val tokenPreferences: TokenPreferences,
    private val remoteSource: RemoteSource,
    private val appExecutors: AppExecutors
): IUserRepository {
    override fun tokenProvider(): Flow<Token> = tokenPreferences.getToken()

    override fun login(email: String, password: String): Flow<Resource<Token>> = flow {
        emit(Resource.Loading())

        remoteSource.login(email, password).collect { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val tokenValue = apiResponse.data.token ?: ""
                    val userId = apiResponse.data.data?.userId ?: ""
                    val token = Token(userId = userId , token = tokenValue, isLogin = true)
                    tokenPreferences.saveToken(token)
                    emit(Resource.Success(token))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("No data found, token is empty"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage ?: "An unknown error occurred"))
                }
            }
        }
    }

    override fun register(email: String, name: String, password: String): Flow<Resource<RegisterResponse>> = flow {
        emit(Resource.Loading())

        remoteSource.register(email, name, password).collect() { apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> {
                    emit(Resource.Error("No Data Found, Token is Empty"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }
        }
    }

    override fun verifyOtpRegister(email: String, otp: String): Flow<Resource<Token>> = flow {
        emit(Resource.Loading())

        remoteSource.verifyOtpRegister(email, otp).collect() {apiResponse ->
            when(apiResponse){
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val tokenValue = apiResponse.data.token ?: ""
                    val userId = apiResponse.data.data?.userId ?: ""
                    val token = Token(userId = userId , token = tokenValue, isLogin = true)
                    tokenPreferences.saveToken(token)
                    emit(Resource.Success(token))
                }
            }
        }
    }
}