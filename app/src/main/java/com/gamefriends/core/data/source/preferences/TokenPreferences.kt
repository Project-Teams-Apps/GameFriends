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
        private val GAMEPLAYED = stringSetPreferencesKey("gamePlayed")
        private val GENDER = stringPreferencesKey("gender")
        private val HOBBY = stringSetPreferencesKey("hobby")
        private val LOCATION = stringPreferencesKey("location")
        private val BIO = stringPreferencesKey("bio")
    }
}