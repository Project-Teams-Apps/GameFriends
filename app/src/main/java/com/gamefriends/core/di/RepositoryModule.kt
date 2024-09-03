package com.gamefriends.core.di

import com.gamefriends.core.data.source.UserRepository
import com.gamefriends.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

@Module(includes = [NetworkModule::class])
@InstallIn
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(userRepository: UserRepository): IUserRepository
}