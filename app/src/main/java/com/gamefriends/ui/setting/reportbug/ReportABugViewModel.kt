package com.gamefriends.ui.setting.reportbug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class ReportABugViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun sendReportBug(bugReport: String) = userUseCase.sendReportABug(bugReport).asLiveData()
}