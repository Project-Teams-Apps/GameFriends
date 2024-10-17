package com.gamefriends.ui.main.chat.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val useCase: UserUseCase): ViewModel() {
    fun fetchHistoryMessage(toUserId: String) = useCase.fetchMessageChatUser(toUserId).asLiveData()

    val getToken = useCase.getToken().asLiveData()
}