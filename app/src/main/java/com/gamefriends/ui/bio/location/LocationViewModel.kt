package com.gamefriends.ui.bio.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun locationUser(location: String) = userUseCase.locationUser(location).asLiveData()
}