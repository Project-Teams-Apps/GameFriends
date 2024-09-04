package com.gamefriends.ui.di

import com.gamefriends.core.domain.usecase.LoginInteractor
import com.gamefriends.core.domain.usecase.LoginUseCase
import com.gamefriends.core.domain.usecase.RegisterInteractor
import com.gamefriends.core.domain.usecase.RegisterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideLoginUseCase(loginInteractor: LoginInteractor): LoginUseCase

    @Binds
    @Singleton
    abstract fun provideRegisterUseCase(registerInteractor: RegisterInteractor): RegisterUseCase
}