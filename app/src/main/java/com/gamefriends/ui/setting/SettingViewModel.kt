package com.gamefriends.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel(){
    fun logoutUser() = userUseCase.logoutUseCase().asLiveData()

    suspend fun logoutDatastore() = userUseCase.deleteDatastore()
}