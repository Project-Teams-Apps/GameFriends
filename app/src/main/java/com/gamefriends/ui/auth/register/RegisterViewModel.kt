package com.gamefriends.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel(){
    fun register(email: String, name: String, password: String) = userUseCase.registerUseCase(email, name, password).asLiveData()
}