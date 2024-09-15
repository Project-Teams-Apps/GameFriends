package com.gamefriends.ui.bio.gameplayed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamePlayedViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun gamePlayedBio(gamePlayed: List<String>) = userUseCase.gamePlayedBio(gamePlayed).asLiveData()
}