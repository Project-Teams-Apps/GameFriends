package com.gamefriends.ui.bio.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gamefriends.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ChooseImageViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun editBioUser(bio: String, gender: String, gamePlayed: List<String>, location: String, hobby: List<String>) = userUseCase.editBioUser(bio, gender, gamePlayed, location, hobby).asLiveData()

    val getBioUser = userUseCase.getBioUser().asLiveData()

    fun uploadProfileImage(file: File) = userUseCase.uploadProfileImage(file).asLiveData()
}