package com.gamefriends.ui.setting.reportbug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BugReportViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun sendBugReport(bugReport: String) = userUseCase.sendBugReport(bugReport).asLiveData()
}