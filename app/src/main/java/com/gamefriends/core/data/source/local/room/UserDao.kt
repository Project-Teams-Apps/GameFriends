package com.gamefriends.core.data.source.local.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gamefriends.core.data.source.local.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

interface UserDao {

    @Query("SELECT token FROM loginDataUser LiMIT 1 ")
    fun getAuthToken(): Flow<LoginEntity>

    @Query("SELECT * FROM loginDataUser")
    fun getAllLoginDataUser(): Flow<LoginEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoginDataUser(loginDataUser: LoginEntity)

    @Delete
    fun deleteLoginDataUser(loginDataUser: LoginEntity)

}