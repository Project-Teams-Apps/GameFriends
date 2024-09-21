package com.gamefriends.ui.bio.gameplayed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamePlayedViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    suspend fun saveGamePlayedUser(gamePlayed: List<String>) = userUseCase.saveGameplayedUser(gamePlayed)
}