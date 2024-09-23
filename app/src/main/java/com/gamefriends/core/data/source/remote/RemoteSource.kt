package com.gamefriends.core.data.source.remote


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gamefriends.core.data.source.local.LocalDataSources
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import com.gamefriends.core.data.source.remote.network.ApiResponse
import com.gamefriends.core.data.source.remote.network.ApiService
import com.gamefriends.core.data.source.remote.response.AddFriendRequestResponse
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.GetProfileResponse
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import com.gamefriends.core.paging.ContentPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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

    @OptIn(ExperimentalPagingApi::class)
    fun getListContentFeed(localDataSources: LocalDataSources, userId: String): Flow<PagingData<FeedUserEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            remoteMediator = ContentPagingSource(localDataSources, apiService, userId),
            pagingSourceFactory = {
                localDataSources.getAllFeedUser()
            }
        ).flow
    }

    fun getProfile(userId: String): Flow<ApiResponse<GetProfileResponse>> = flow {
        try {
            val response = apiService.getProfile(userId)

            if (response.data?.name?.isNotEmpty() == true) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun addFriendRequest(userRequestId: String, userAcceptId: String):Flow<ApiResponse<AddFriendRequestResponse>> = flow {
        try {
            val response = apiService.addFriendRequest(userRequestId, userAcceptId)

            if (response.data?.userRequestId?.isNotEmpty() == true)  {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }



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

    suspend fun editBioUser(userId: String, bio: String, gender: String, gamePlayedString: List<String>, location: String, hobby: List<String>): Flow<ApiResponse<BioResponse>> =
        flow {
            try {
                val requestBody = DTO.EditBioUser(bio,gender,gamePlayedString,location,hobby)
                val response = apiService.editBioUser(userId, requestBody)

                if (response.data?.bio?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun uploadProfileImage(userId: String, file: File):Flow<ApiResponse<BioResponse>> = flow {
        try {
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val multiPartbody = MultipartBody.Part.createFormData(
                "file",file.name, requestImageFile
            )

            val response = apiService.uploadImageProfile(userId, multiPartbody)

            if (response.data?.profilePicureUrl?.isNotEmpty() == true) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }
}