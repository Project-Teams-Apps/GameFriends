package com.gamefriends.ui.di

import com.gamefriends.core.domain.usecase.LoginInteractor
import com.gamefriends.core.domain.usecase.LoginUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Provides
    abstract fun provideLoginUseCase(loginInteractor: LoginInteractor): LoginUseCase
}