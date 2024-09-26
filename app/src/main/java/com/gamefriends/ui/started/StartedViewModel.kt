package com.gamefriends.ui.started

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartedViewModel @Inject constructor(private val useCase: UserUseCase): ViewModel(){
    val getToken = useCase.getToken().asLiveData()
}