package com.gamefriends.core.domain.usecase


import com.gamefriends.core.domain.repository.IUserRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val userRepository: IUserRepository): LoginUseCase {
    override fun loginUseCase(email: String, password: String) = userRepository.login(email, password)

}