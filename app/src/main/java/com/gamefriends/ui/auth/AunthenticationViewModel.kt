package com.gamefriends.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AunthenticationViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    val getToken = userUseCase.getToken().asLiveData()
}