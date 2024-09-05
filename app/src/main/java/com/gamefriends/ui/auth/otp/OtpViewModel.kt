package com.gamefriends.ui.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(private val userRepository: IUserRepository): ViewModel() {
    fun verifyOtpRegister(email: String, otp: String) = userRepository.verifyOtpRegister(email,otp).asLiveData()
}