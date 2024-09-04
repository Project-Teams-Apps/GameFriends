package com.gamefriends.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase): ViewModel(){
    fun register(email: String, name: String, password: String) = registerUseCase.registerUseCase(email, name, password).asLiveData()
}