package com.gamefriends.core.data.source.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gamefriends.core.domain.model.BioUser
import com.gamefriends.core.domain.model.ProfileUser
import com.gamefriends.core.domain.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    fun getToken(): Flow<Token> {
        return dataStore.data.map { preferences ->
            Token(
                preferences[USERID] ?: "",
                preferences[TOKEN] ?: "",
                preferences[IS_LOGIN] ?: false
            )
        }
    }

    fun getBioUser(): Flow<BioUser> {
        return dataStore.data.map { preferences ->
            BioUser(
                preferences[GAMEPLAYED]?.toList() ?: emptyList(),
                preferences[GENDER] ?: "",
                preferences[HOBBY]?.toList() ?: emptyList(),
                preferences[LOCATION] ?: "",
                preferences[BIO] ?: ""
            )
        }
    }

    fun getProfileUser(): Flow<ProfileUser> {
        return dataStore.data.map { preferences ->
            ProfileUser(
                preferences[USERID] ?: "",
                preferences[nameUser] ?: "",
                preferences[emailUser] ?: "",
                preferences[bioUser] ?: "",
                preferences[genderUser] ?: "",
                preferences[gamePlayedUser]?.toList() ?: emptyList(),
                preferences[hobbyUser]?.toList() ?: emptyList(),
                preferences[locationUser] ?: "",
                preferences[profilePictureUrl] ?: ""
            )
        }
    }

    suspend fun saveProfileUser(profileUser: ProfileUser) {
        dataStore.edit { preferences ->
            preferences[USERID] = profileUser.userId
            preferences[nameUser] = profileUser.name
            preferences[emailUser] = profileUser.email
            preferences[bioUser] = profileUser.bio
            preferences[genderUser] = profileUser.gender
            preferences[gamePlayedUser] = profileUser.gamePlayed.toSet()
            preferences[hobbyUser] = profileUser.hobby.toSet()
            preferences[locationUser] = profileUser.location
            preferences[profilePictureUrl] = profileUser.profilePictureUrl
        }
    }



    suspend fun saveGamePlayedUser(bioUser: BioUser) {
        dataStore.edit { preferences ->
            preferences[GAMEPLAYED] = bioUser.gamePlayed.toSet()
        }
    }

    suspend fun saveGenderUser(bioUser: BioUser) {
        dataStore.edit { preferences ->
            preferences[GENDER] = bioUser.gender
        }
    }

    suspend fun saveHobbyUser(bioUser: BioUser) {
        dataStore.edit { preferences ->
            preferences[HOBBY] = bioUser.hobby.toSet()
        }
    }

    suspend fun saveLocationUser(bioUser: BioUser) {
        dataStore.edit { preferences ->
            preferences[LOCATION] = bioUser.location
        }
    }

    suspend fun saveBioUser(bioUser: BioUser) {
        dataStore.edit { preferences ->
            preferences[BIO] = bioUser.bio
        }
    }

    suspend fun saveToken(token: Token) {
        dataStore.edit { preferences ->
            preferences[USERID] = token.userId
            preferences[TOKEN] = token.token
            preferences[IS_LOGIN] = true
        }
    }

    suspend fun logout(){
        dataStore.edit {preferences ->
            preferences.clear()
        }
    }


    companion object {
        private val USERID = stringPreferencesKey("userId")
        private val TOKEN = stringPreferencesKey("token")
        private val IS_LOGIN = booleanPreferencesKey("isLogin")
        private val nameUser = stringPreferencesKey("name")
        private val emailUser = stringPreferencesKey("emailUser")
        private val profilePictureUrl = stringPreferencesKey("profilePictureUrl")
        private val locationUser = stringPreferencesKey("locationUser")
        private val genderUser = stringPreferencesKey("genderUser")
        private val bioUser = stringPreferencesKey("bioUser")
        private val gamePlayedUser = stringSetPreferencesKey("gamePlayedUser")
        private val hobbyUser = stringSetPreferencesKey("hobbyUser")

        private val GAMEPLAYED = stringSetPreferencesKey("gamePlayed")
        private val GENDER = stringPreferencesKey("gender")
        private val HOBBY = stringSetPreferencesKey("hobby")
        private val LOCATION = stringPreferencesKey("location")
        private val BIO = stringPreferencesKey("bio")

    }
}