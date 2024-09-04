package com.gamefriends.core.domain.usecase

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import com.gamefriends.core.domain.model.Token
import com.gamefriends.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase {
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

    override fun verifyOtpUseCase(email: String, otp: String): Flow<Resource<VerifyRegisterResponse>> {
        return userRepository.verifyOtpRegister(email, otp)
    }

}