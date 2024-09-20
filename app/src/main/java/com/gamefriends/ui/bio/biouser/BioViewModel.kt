package com.gamefriends.ui.bio.biouser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BioViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    suspend fun saveBioUser(bio: String) = userUseCase.saveBioUser(bio)
}