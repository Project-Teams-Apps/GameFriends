package com.gamefriends.core.domain.usecase

import androidx.paging.PagingData
import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import com.gamefriends.core.data.source.remote.response.AddFriendRequestResponse
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.ListItem
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import com.gamefriends.core.domain.model.BioUser
import com.gamefriends.core.domain.model.Token
import com.gamefriends.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase {
    override fun getToken(): Flow<Token> {
        return userRepository.tokenProvider()
    }

    override fun getBioUser(): Flow<BioUser> {
        return userRepository.bioUserProvider()
    }

    override fun loginUseCase(email: String, password: String): Flow<Resource<Token>> {
        return userRepository.login(email, password)
    }

    override fun registerUseCase(
        email: String,
        name: String,
        password: String,
    ): Flow<Resource<RegisterResponse>> {
        return userRepository.register(email, name, password)
    }

    override fun verifyOtpUseCase(email: String, otp: String): Flow<Resource<Token>> {
        return userRepository.verifyOtpRegister(email, otp)
    }

    override fun fetchListContent(): Flow<PagingData<FeedUserEntity>> {
        return userRepository.fetchListContent()
    }

    override fun addFriendRequest(userAcceptId: String): Flow<Resource<AddFriendRequestResponse>> {
        return userRepository.addFriendRequest(userAcceptId)
    }

    override fun uploadProfileImage(file: File): Flow<Resource<BioResponse>> {
        return userRepository.uploadProfileImage(file)
    }

    override fun gamePlayedBio(gamePlayed: List<String>): Flow<Resource<BioResponse>> {
        return userRepository.gamePlayedBio(gamePlayed)
    }

    override fun genderBio(gendrBioString: String): Flow<Resource<BioResponse>> {
        return userRepository.genderBio(gendrBioString)
    }

    override fun hobbyBio(hobby: List<String>): Flow<Resource<BioResponse>> {
        return userRepository.hobbyBio(hobby)
    }

    override fun bioUser(bio: String): Flow<Resource<BioResponse>> {
        return userRepository.bioUser(bio)
    }

    override fun locationUser(location: String): Flow<Resource<BioResponse>> {
        return userRepository.locationUser(location)
    }

    override suspend fun saveGameplayedUser(gamePlayed: List<String>) {
        return userRepository.saveGamePlayedUser(gamePlayed)
    }

    override suspend fun saveGenderUser(gender: String) {
        return userRepository.saveGenderUser(gender)
    }

    override suspend fun saveHobbyUser(hobby: List<String>) {
        return userRepository.saveHobbyUser(hobby)
    }

    override suspend fun saveLocationUser(location: String) {
        return userRepository.userLocation(location)
    }

    override suspend fun saveBioUser(bio: String)  = userRepository.bioDataUser(bio)

    override fun editBioUser(
        bio: String,
        gender: String,
        gamePlayed: List<String>,
        location: String,
        hobby: List<String>,
    ): Flow<Resource<BioResponse>> {
        return userRepository.editBioUser(bio, gender, gamePlayed, location, hobby)
    }

}