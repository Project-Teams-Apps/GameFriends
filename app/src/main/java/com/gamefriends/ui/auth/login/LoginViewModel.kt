package com.gamefriends.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: LoginUseCase): ViewModel() {
    fun login(email: String, password: String) = useCase.loginUseCase(email, password).asLiveData()
}