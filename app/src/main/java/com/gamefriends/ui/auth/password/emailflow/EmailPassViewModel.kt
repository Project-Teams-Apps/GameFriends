package com.gamefriends.ui.auth.password.emailflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailPassViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun changePassword(email: String) = userUseCase.changePasswordUseCase(email).asLiveData()
}