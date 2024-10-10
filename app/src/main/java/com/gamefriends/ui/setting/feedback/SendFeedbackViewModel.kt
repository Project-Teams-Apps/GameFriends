package com.gamefriends.ui.setting.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SendFeedbackViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun sendFeedback(feedbackReport: String) = userUseCase.sendFeedback(feedbackReport).asLiveData()
}