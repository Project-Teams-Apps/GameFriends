package com.gamefriends.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.gamefriends.core.data.source.local.room.Database
import com.gamefriends.core.data.source.local.room.dao.FeedDao
import com.gamefriends.core.data.source.local.room.dao.MessageDao
import com.gamefriends.core.data.source.local.room.dao.RemoteDao
import com.gamefriends.core.data.source.preferences.TokenPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

//    val passphrase: ByteArray = SQLiteDatabase.getBytes("evenxx".toCharArray())
//    val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): Database = Room.databaseBuilder(
        context,
        Database::class.java,
        "feedUserDb"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providesFeedDao(database: Database): FeedDao = database.feedDao()

    @Provides
    fun providesRemoteDao(database: Database): RemoteDao = database.remoteDao()

    @Provides
    fun provideMessageDao(database: Database): MessageDao = database.messageDao()


    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun providesTokenPreferences(dataStore: DataStore<Preferences>): TokenPreferences {
        return TokenPreferences(dataStore)
    }
}