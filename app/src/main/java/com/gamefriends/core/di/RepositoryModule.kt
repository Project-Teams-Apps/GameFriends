package com.gamefriends.core.di

import com.gamefriends.core.data.source.UserRepository
import com.gamefriends.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(userRepository: UserRepository): IUserRepository
}