package com.flyboy.fighters.di

import android.content.Context
import androidx.room.Room
import com.flyboy.fighters.data.local.AppDatabase
import com.flyboy.fighters.data.local.FighterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    fun provideFighterDao(appDatabase: AppDatabase): FighterDao {
        return appDatabase.fighterDao()
    }
    @Provides
    @Singleton
    fun injectStringSamle():String{
return  "Salammmmmmmmmmmmm"
    }
}