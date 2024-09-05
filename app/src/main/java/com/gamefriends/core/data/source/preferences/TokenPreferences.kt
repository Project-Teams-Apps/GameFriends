package com.gamefriends.core.data.source.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
    }
}