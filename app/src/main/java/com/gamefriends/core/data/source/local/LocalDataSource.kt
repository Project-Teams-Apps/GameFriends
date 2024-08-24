package com.gamefriends.core.data.source.local

import com.gamefriends.core.data.source.local.entity.LoginEntity
import com.gamefriends.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val userDao: UserDao) {

    fun getTokenUserData(): Flow<LoginEntity> = userDao.getAuthToken()

    fun getAllLoginUserData(): Flow<LoginEntity>    = userDao.getAllLoginDataUser()

    fun insertLoginUserData(loginUserData: LoginEntity) =  userDao.insertLoginDataUser(loginUserData)

    fun deleteLoginUserData(loginUserData: LoginEntity) = userDao.deleteLoginDataUser(loginUserData)


}