package com.gamefriends.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gamefriends.core.data.source.local.entity.LoginEntity

@Database(entities = [LoginEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract fun loginUserDao(): UserDao

}