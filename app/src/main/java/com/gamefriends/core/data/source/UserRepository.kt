package com.gamefriends.core.data.source

import androidx.paging.PagingData

import com.gamefriends.core.data.source.local.LocalDataSources
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import com.gamefriends.core.data.source.preferences.TokenPreferences
import com.gamefriends.core.data.source.remote.RemoteSource
import com.gamefriends.core.data.source.remote.network.ApiResponse
import com.gamefriends.core.data.source.remote.response.AddFriendRequestResponse
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.LogoutResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.domain.model.BioUser
import com.gamefriends.core.domain.model.ProfileUser
import com.gamefriends.core.domain.model.Token
import com.gamefriends.core.domain.repository.IUserRepository
import com.gamefriends.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val tokenPreferences: TokenPreferences,
    private val remoteSource: RemoteSource,
    private val localDataSources: LocalDataSources,
    private val appExecutors: AppExecutors
): IUserRepository {
    override fun tokenProvider(): Flow<Token> = tokenPreferences.getToken()

    override fun bioUserProvider(): Flow<BioUser> = tokenPreferences.getBioUser()

    override fun profileUserProvider(): Flow<ProfileUser> = tokenPreferences.getProfileUser()

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

    override fun logout(): Flow<Resource<LogoutResponse>> = flow {
        emit(Resource.Loading())
        val userId = tokenPreferences.getToken().first().userId
        val email = tokenPreferences.getProfileUser().first().email
        remoteSource.logoutUser(userId, email).collect() {apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> {
                    emit(Resource.Error("No data found, token is empty"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage ?: "An unknown error occurred"))
                }
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }
        }

    }

    override suspend fun deleteDataStore(){
        tokenPreferences.logout()
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

    override fun editBioUser(
        bio: String,
        gender: String,
        gamePlayed: List<String>,
        location: String,
        hobby: List<String>,
    ): Flow<Resource<BioResponse>> = flow {
        emit(Resource.Loading())

        val userId = runBlocking { tokenPreferences.getToken().first().userId }
        remoteSource.editBioUser(userId, bio, gender, gamePlayed, location, hobby).collect() {apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }
        }
    }

    override fun uploadProfileImage(file: File): Flow<Resource<BioResponse>>  = flow{
        emit(Resource.Loading())

        val userId = runBlocking { tokenPreferences.getToken().first().userId  }
        remoteSource.uploadProfileImage(userId, file).collect() {apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }
        }
    }

    override fun fetchListContent(): Flow<PagingData<FeedUserEntity>>{
        return flow {
            val userId = tokenPreferences.getToken().first().userId
            emitAll(remoteSource.getListContentFeed(localDataSources, userId))
        }
    }

    override fun getProfileUser(): Flow<Resource<ProfileUser>> = flow {
        emit(Resource.Loading())

        val userId = tokenPreferences.getToken().first().userId

        remoteSource.getProfile(userId).collect() { apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val data = apiResponse.data.data
                    val name = data?.name ?: ""
                    val email = data?.email ?: ""
                    val bio = data?.bioUser?.bio ?: ""
                    val gender = data?.bioUser?.gender ?: ""
                    val gamePlayed = data?.bioUser?.gamePlayed as List<String>
                    val hobby = data.bioUser.hobby as List<String>
                    val location = data.bioUser.location ?: ""
                    val profilePictureUrl = data.bioUser.profilePicureUrl ?: ""
                    val profileUser = ProfileUser(userId, name, email, bio, gender, gamePlayed, hobby, location, profilePictureUrl)
                    tokenPreferences.saveProfileUser(profileUser)
                    emit(Resource.Success(profileUser))
                }
            }
        }
    }

    override fun addFriendRequest(userAcceptId: String): Flow<Resource<AddFriendRequestResponse>> = flow {
        emit(Resource.Loading())

        val userRequestId = tokenPreferences.getToken().first().userId
        remoteSource.addFriendRequest(userRequestId, userAcceptId).collect() {apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }
        }
    }

    override suspend fun saveGamePlayedUser(gamePlayed: List<String>) {
        val bioUser = BioUser(gamePlayed = gamePlayed)
        tokenPreferences.saveGamePlayedUser(bioUser)
    }

    override suspend fun saveGenderUser(gender: String) {
        val bioUser = BioUser(gender = gender)
        tokenPreferences.saveGenderUser(bioUser)
    }

    override suspend fun saveHobbyUser(hobby: List<String>) {
        val bioUser = BioUser(hobby = hobby)
        tokenPreferences.saveHobbyUser(bioUser)
    }

    override suspend fun bioDataUser(bio: String) {
        val bioUser = BioUser(bio = bio)
        tokenPreferences.saveBioUser(bioUser)
    }

    override suspend fun userLocation(location: String) {
        val bioUser = BioUser(location = location)
        tokenPreferences.saveLocationUser(bioUser)
    }

    override fun gamePlayedBio(gamePlayed: List<String>): Flow<Resource<BioResponse>> = flow {
        emit(Resource.Loading())
        val userId = runBlocking { tokenPreferences.getToken().first().userId }
        remoteSource.gamePlayedBio(userId, gamePlayed).collect() {apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }
        }
    }

    override fun genderBio(genderBioString: String): Flow<Resource<BioResponse>> = flow {
        emit(Resource.Loading())

        val userId = runBlocking { tokenPreferences.getToken().first().userId }
        remoteSource.genderBio(userId, genderBioString).collect() {apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }
        }
    }

    override fun hobbyBio(hobby: List<String>): Flow<Resource<BioResponse>> = flow {
        emit(Resource.Loading())

        val userId = runBlocking { tokenPreferences.getToken().first().userId }
        remoteSource.hobbyBio(userId, hobby).collect() {apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }
        }
    }

    override fun bioUser(bio: String): Flow<Resource<BioResponse>>  = flow {
        emit(Resource.Loading())

        val userId = runBlocking { tokenPreferences.getToken().first().userId }
        remoteSource.bioUser(userId, bio).collect() {apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }
        }
    }

    override fun locationUser(location: String): Flow<Resource<BioResponse>> = flow {
        emit(Resource.Loading())

        val userId = runBlocking { tokenPreferences.getToken().first().userId }
        remoteSource.locationUser(userId, location).collect() {apiResponse ->
            when(apiResponse) {
                ApiResponse.Empty -> emit(Resource.Error("No Data Found"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Success -> {
                    val response = apiResponse.data
                    emit(Resource.Success(response))
                }
            }

        }
    }


}