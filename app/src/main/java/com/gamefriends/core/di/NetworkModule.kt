package com.gamefriends.core.di

import com.gamefriends.BuildConfig
import com.gamefriends.core.data.source.preferences.TokenPreferences
import com.gamefriends.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    fun providesOkHttpClient(tokenPreferences: TokenPreferences): OkHttpClient {

        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val token = runBlocking {
                tokenPreferences.getToken().first().token
            }


            val requestWithAuth = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            chain.proceed(requestWithAuth)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

}