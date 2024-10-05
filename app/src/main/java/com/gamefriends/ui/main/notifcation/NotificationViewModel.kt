package com.gamefriends.ui.main.notifcation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun fetchListRequestNotification() = userUseCase.fetchListRequestFriend().asLiveData()

    fun acceptFriendRequest(userRequestId: String) = userUseCase.acceptFriendRequest(userRequestId).asLiveData()
}