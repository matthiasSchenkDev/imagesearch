package com.example.imagesearch.app.di

import android.content.Context
import androidx.room.Room
import com.example.imagesearch.data.db.AppDatabase
import com.example.imagesearch.data.db.ImageDao
import com.example.imagesearch.data.db.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    companion object {
        private const val DATABASE_NAME = "image-search-database"
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    fun provideImageDao(appDatabase: AppDatabase): ImageDao = appDatabase.imageDao()

    @Provides
    fun provideSearchDao(appDatabase: AppDatabase): SearchDao = appDatabase.searchDao()

}