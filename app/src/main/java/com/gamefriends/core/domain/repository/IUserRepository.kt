package com.gamefriends.core.domain.repository

import androidx.paging.PagingData
import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.ListItem
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import com.gamefriends.core.domain.model.BioUser
import com.gamefriends.core.domain.model.Token
import kotlinx.coroutines.flow.Flow
import java.io.File

interface IUserRepository {
    fun tokenProvider(): Flow<Token>

    fun bioUserProvider(): Flow<BioUser>

    fun login(email: String, password: String): Flow<Resource<Token>>

    fun register(email: String, name: String, password: String): Flow<Resource<RegisterResponse>>

    fun verifyOtpRegister(email: String, otp: String): Flow<Resource<Token>>

    fun editBioUser(bio: String, gender: String, gamePlayed: List<String>, location: String, hobby: List<String>): Flow<Resource<BioResponse>>

    fun uploadProfileImage(file: File): Flow<Resource<BioResponse>>

    fun fetchListContent(): Flow<PagingData<FeedUserEntity>>

    suspend fun saveGamePlayedUser(gamePlayed: List<String>)

    suspend fun saveGenderUser(gender: String)

    suspend fun saveHobbyUser(hobby: List<String>)

    suspend fun bioDataUser(bio: String)

    suspend fun userLocation(location: String)

    fun gamePlayedBio(gamePlayed: List<String>): Flow<Resource<BioResponse>>

    fun genderBio(genderBioString: String): Flow<Resource<BioResponse>>

    fun hobbyBio(hobby: List<String>): Flow<Resource<BioResponse>>

    fun bioUser(bio: String): Flow<Resource<BioResponse>>

    fun locationUser(location: String): Flow<Resource<BioResponse>>
}