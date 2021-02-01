package com.flyboy.fighters.di

//import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

//import kotlinx.serialization.json.Json

import com.flyboy.fighters.Config
import com.flyboy.fighters.data.remote.FightersRemoteDataSource
import com.flyboy.fighters.data.remote.FightersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    private const val baseUrl = Config.BASE_URL

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor;
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
           .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
//    @Provides
//    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideFighterService(retrofit: Retrofit): FightersService = retrofit.create(FightersService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: FightersService) = FightersRemoteDataSource(characterService)

}