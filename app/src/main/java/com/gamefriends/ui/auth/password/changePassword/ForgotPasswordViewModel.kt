package com.gamefriends.ui.auth.password.changePassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun changePassword(email: String, otp: String, password: String) = userUseCase.changePasswordUserUseCase(email, otp, password).asLiveData()
}