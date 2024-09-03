package com.gamefriends.core.data.source

import com.gamefriends.core.data.source.preferences.TokenPreferences
import com.gamefriends.core.data.source.remote.RemoteSource
import com.gamefriends.core.data.source.remote.response.ErrorResponse
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

class UserRepository @Inject constructor(
    private val tokenPreferences: TokenPreferences,
    private val remoteSource: RemoteSource,
    private val appExecutors: AppExecutors
): IUserRepository {
    override fun tokenProvider(): Flow<Token> = tokenPreferences.getToken()

    override fun login(email: String, password: String): Flow<Resource<Token>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = remoteSource.login(email, password)
                val token = Token(
                    token = runBlocking { response.map { tokenProvider().map { it } }.toString() },
                    isLogin = true
                )
                tokenPreferences.saveToken(token)

                emit(Resource.Success(token))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.toString()))
            }
        }


}