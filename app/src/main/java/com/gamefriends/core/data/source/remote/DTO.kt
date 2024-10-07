package com.gamefriends.core.data.source.remote

object DTO {

    data class LoginBody(
        var email: String,
        var password: String
    )

    data class RegisterBody(
        var email: String,
        var name: String,
        var password: String
    )

    data class VerifyOtpRegisterBody(
        var email: String,
        var otp: String
    )

    data class EditBioUser(
        var bio: String,
        var gender: String,
        var gamePlayed: List<String>,
        var location: String,
        var hobby: List<String>
    )

    data class feedbackUserbody(
        var email: String,
        var feedbackReport: String
    )

    data class reportBugBody(
        var email: String,
        var bugReport: String
    )

    data class EmailBody(
        var email: String
    )

    data class ChangePasswordBody(
        var email: String,
        var otp: String,
        var password: String
    )

    data class LogoutBody(
        var userID: String,
        var email: String
    )

    data class UserIdBody(
        var userID: String
    )

    data class GamePlayedBody(
        var gamePlayed: List<String>
    )

    data class GenderBody(
        var gender: String
    )

    data class HobbyBody(
        var hobby: List<String>
    )

    data class LocationBody(
        var location: String
    )

    data class BioBody(
        var bio: String
    )

}