package com.gamefriends.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import com.gamefriends.core.data.source.local.enitity.MessageEntity
import com.gamefriends.core.data.source.local.enitity.RemoteEntity
import com.gamefriends.core.data.source.local.room.dao.FeedDao
import com.gamefriends.core.data.source.local.room.dao.MessageDao
import com.gamefriends.core.data.source.local.room.dao.RemoteDao
import com.gamefriends.core.utils.ConverterType


@Database(
    entities = [FeedUserEntity::class, RemoteEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ConverterType::class)
abstract class Database(): RoomDatabase(){

    abstract fun feedDao(): FeedDao
    abstract fun remoteDao(): RemoteDao
    abstract fun messageDao(): MessageDao
}