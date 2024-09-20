package com.gamefriends.core.domain.usecase

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

interface UserUseCase {
    fun getToken(): Flow<Token>

    fun getBioUser(): Flow<BioUser
            >
    fun loginUseCase(email: String, password: String): Flow<Resource<Token>>

    fun registerUseCase(email: String, name: String, password: String): Flow<Resource<RegisterResponse>>

    fun verifyOtpUseCase(email: String, otp: String): Flow<Resource<Token>>

    fun fetchListContent(): Flow<PagingData<FeedUserEntity>>

    fun uploadProfileImage(file: File): Flow<Resource<BioResponse>>

    fun gamePlayedBio(gamePlayed: List<String>): Flow<Resource<BioResponse>>

    fun genderBio(gendrBioString: String): Flow<Resource<BioResponse>>

    fun hobbyBio(hobby: List<String>): Flow<Resource<BioResponse>>

    fun bioUser(bio: String): Flow<Resource<BioResponse>>

    fun locationUser(location: String): Flow<Resource<BioResponse>>

    suspend fun saveGameplayedUser(gamePlayed: List<String>)

    suspend fun saveGenderUser(gender: String)

    suspend fun saveHobbyUser(hobby: List<String>)

    suspend fun saveLocationUser(location: String)

    suspend fun saveBioUser(bio: String)

    fun editBioUser(bio: String, gender: String, gamePlayed: List<String>, location: String, hobby: List<String>): Flow<Resource<BioResponse>>
}