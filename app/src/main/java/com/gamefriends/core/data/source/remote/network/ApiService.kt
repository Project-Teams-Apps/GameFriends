package com.gamefriends.core.data.source.remote.network

import com.gamefriends.core.data.source.remote.DTO
import com.gamefriends.core.data.source.remote.response.AddFriendRequestResponse
import com.gamefriends.core.data.source.remote.response.BioResponse
import com.gamefriends.core.data.source.remote.response.GetProfileResponse
import com.gamefriends.core.data.source.remote.response.ListNotificationResponse
import com.gamefriends.core.data.source.remote.response.ListResponse
import com.gamefriends.core.data.source.remote.response.LoginResponse
import com.gamefriends.core.data.source.remote.response.LogoutResponse
import com.gamefriends.core.data.source.remote.response.RegisterResponse
import com.gamefriends.core.data.source.remote.response.VerifyRegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginBody: DTO.LoginBody
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerBody: DTO.RegisterBody
    ): RegisterResponse

    @POST("auth/register/verify")
    suspend fun verifyOtpRegister(
        @Body verifyOtpRegisterBody: DTO.VerifyOtpRegisterBody
    ): VerifyRegisterResponse

    @POST("auth/forgotpassword")
    suspend fun forgotPassword(
        @Body forgotPasswordBody: DTO.EmailBody
    ): RegisterResponse

    @POST("auth/changepassword")
    suspend fun changePassword(
        @Body changePasswordBody: DTO.ChangePasswordBody
    ): RegisterResponse

    @GET("api/content")
    suspend fun getListContent(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 5,
        @Query("userId") userId: String
    ): ListResponse

    // List Request Friend To User
    @GET("/api/content/request/friend")
    suspend fun listRequestFriend(
        @Query("userId") userId: String
    ): ListNotificationResponse

    // add request to add friend
    @POST("/api/content/friend/request")
    suspend fun addFriendRequest(
        @Query("userRequestId") userRequestId: String,
        @Query("userAcceptId") userAcceptId: String
    ): AddFriendRequestResponse

    // accept a friend Request
    @POST("/api/content/friend/accept")
    suspend fun acceptFriendRequest(
        @Query("userRequestId") userRequestId: String,
        @Query("userAcceptId") userAcceptId: String
    ): AddFriendRequestResponse

    @GET("/profile/user")
    suspend fun getProfile(
        @Query("userId") userId: String
    ): GetProfileResponse

    @POST("/auth/logout")
    suspend fun logoutUser(
        @Body logoutBody: DTO.LogoutBody
    ): LogoutResponse

    @PATCH("profile/{id}")
    suspend fun gamePlayedBio(
        @Path("id") id: String,
        @Body gamePlayedBody: DTO.GamePlayedBody
    ): BioResponse

    @PATCH("profile/{id}")
    suspend fun genderBio(
        @Path("id") id: String,
        @Body genderBody: DTO.GenderBody
    ): BioResponse

    @POST("profile/{id}")
    suspend fun hobbyBio(
        @Path("id") id: String,
        @Body hobbyBody: DTO.HobbyBody
    ): BioResponse

    @POST("profile/{id}")
    suspend fun locationBio(
        @Path("id") id:String,
        @Body locationBody: DTO.LocationBody
    ): BioResponse

    @POST("profile/{id}")
    suspend fun bioBio(
        @Path("id") id: String,
        @Body bioBody: DTO.BioBody
    ): BioResponse

    @PATCH("profile/{id}")
    suspend fun editBioUser(
        @Path("id") id:String,
        @Body editBioUser: DTO.EditBioUser
    ): BioResponse

    @Multipart
    @PATCH("profile/picture/{id}")
    suspend fun uploadImageProfile(
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): BioResponse

}