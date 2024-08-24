package com.gamefriends.core.domain.usecase

import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.domain.model.LoginUserData
import com.gamefriends.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserInteractor @Inject constructor(private val userRepository: IUserRepository): LoginUseCase {
    override fun getAllLoginUserData(
        email: String,
        password: String,
    ): Flow<Resource<LoginUserData>> = userRepository.getAllLoginUserData(email, password)

}