package com.gamefriends.core.domain.usecase

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import com.gamefriends.core.domain.model.Token
import com.gamefriends.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase {
    override fun getToken(): Flow<Token> {
        return userRepository.tokenProvider()
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


}