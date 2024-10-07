package com.gamefriends.ui.main.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListChatViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun fetchListChat() = userUseCase.fetchListChat().asLiveData()
}