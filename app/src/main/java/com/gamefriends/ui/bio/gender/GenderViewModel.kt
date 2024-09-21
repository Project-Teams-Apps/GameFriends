package com.gamefriends.ui.bio.gender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {

    suspend fun saveGenderUser(gender: String) = userUseCase.saveGenderUser(gender)
}