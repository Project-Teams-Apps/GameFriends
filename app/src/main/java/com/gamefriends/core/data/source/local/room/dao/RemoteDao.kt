package com.gamefriends.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gamefriends.core.data.source.local.enitity.RemoteEntity

@Dao
interface RemoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStories(remoteKey: List<RemoteEntity>)

    @Query("SELECT * FROM remoteEntity WHERE ID = :id")
    suspend fun getRemoteKeys(id:String): RemoteEntity?

    @Query("DELETE FROM remoteEntity")
    suspend fun deleteRemoteKeys()
}