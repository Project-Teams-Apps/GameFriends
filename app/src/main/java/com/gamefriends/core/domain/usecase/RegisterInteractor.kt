package com.gamefriends.core.domain.usecase

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterInteractor @Inject constructor(private val userRepository: IUserRepository): RegisterUseCase{
    override fun registerUseCase(
        email: String,
        name: String,
        password: String,
    ): Flow<Resource<RegisterResponse>> {
        return userRepository.register(email, name, password)
    }

}