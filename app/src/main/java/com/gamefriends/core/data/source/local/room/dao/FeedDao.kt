package com.gamefriends.core.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import java.util.concurrent.Flow

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeedUser(feedUserEntity: FeedUserEntity)

    @Query("SELECT * FROM feedUser")
    fun getAllFeedUser(): PagingSource<Int,FeedUserEntity>

    @Query("DELETE from feedUser")
    fun deleteAllFeedUser()
}