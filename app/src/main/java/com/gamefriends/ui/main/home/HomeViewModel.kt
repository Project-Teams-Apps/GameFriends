package com.gamefriends.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import com.gamefriends.core.data.source.remote.response.ListItem
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun fetchContentFeed(): Flow<PagingData<FeedUserEntity>> = userUseCase.fetchListContent().cachedIn(viewModelScope)

    fun addFriendRequest(userAcceptId: String) = userUseCase.addFriendRequest(userAcceptId).asLiveData()
}