package com.gamefriends.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gamefriends.core.data.source.local.enitity.MessageEntity

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(messageEntity: MessageEntity)

    @Query("DELETE FROM messages WHERE to_userId=:to_userId")
    fun deleteMessage(to_userId: String)
}