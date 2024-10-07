package com.gamefriends.core.data.source.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import com.gamefriends.core.data.source.local.enitity.MessageEntity
import com.gamefriends.core.data.source.local.enitity.RemoteEntity
import com.gamefriends.core.data.source.local.room.Database
import com.gamefriends.core.data.source.local.room.dao.FeedDao
import com.gamefriends.core.data.source.local.room.dao.MessageDao
import com.gamefriends.core.data.source.local.room.dao.RemoteDao


import javax.inject.Inject

class LocalDataSources @Inject constructor(
    private val feedDao: FeedDao,
    private val remoteDao: RemoteDao,
    private val messageDao: MessageDao,
    private val feedDatabase: Database
) {

    fun getAllFeedUser(): PagingSource<Int, FeedUserEntity> = feedDao.getAllFeedUser()

    fun insertFeedUser(feedUserEntity: FeedUserEntity) = feedDao.insertFeedUser(feedUserEntity)

    fun deleteFeedUser() = feedDao.deleteAllFeedUser()

    fun insertMessage(messageEntity: MessageEntity) = messageDao.insertMessage(messageEntity)

    fun deleteMessage(to_userId: String) = messageDao.deleteMessage(to_userId)

    suspend fun insertRemoteKeys(remoteEntity: List<RemoteEntity>) = remoteDao.insertAllStories(remoteEntity)

    suspend fun getAllRemoteKeys(id: String) = remoteDao.getRemoteKeys(id)

    suspend fun deleteAllRemoteKeys() = remoteDao.deleteRemoteKeys()

    suspend fun withTransaction(block: suspend () -> Unit) {
        feedDatabase.withTransaction {
            block()
        }
    }
}