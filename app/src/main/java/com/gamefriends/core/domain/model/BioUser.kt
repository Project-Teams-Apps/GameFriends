package com.gamefriends.core.domain.model

data class BioUser(
    var gamePlayed: List<String> = emptyList(),
    var gender: String = "",
    var hobby: List<String> = emptyList(),
    var location: String = "",
    var bio: String = ""
)
