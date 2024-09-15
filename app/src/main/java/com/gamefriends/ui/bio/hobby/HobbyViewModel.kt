package com.gamefriends.ui.bio.hobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HobbyViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun hobbyBio(hobby: List<String>) = userUseCase.hobbyBio(hobby).asLiveData()
}