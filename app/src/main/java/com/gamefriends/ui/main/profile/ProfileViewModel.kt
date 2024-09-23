package com.gamefriends.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun getProfileUser() = userUseCase.profileUseCase().asLiveData()

    val getProfileData = userUseCase.getProfileDataStore().asLiveData()
}