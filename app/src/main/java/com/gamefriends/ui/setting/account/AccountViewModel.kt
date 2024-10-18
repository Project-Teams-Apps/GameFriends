package com.gamefriends.ui.setting.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel(){
    fun getProfileUser() = userUseCase.profileUseCase().asLiveData()

    fun changePassword(email: String, otp: String, password:String) = userUseCase.changePasswordUserUseCase(email, otp, password)

    val getProfileData = userUseCase.getProfileDataStore().asLiveData()
}