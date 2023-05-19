package com.example.imagesearch.app.di

import com.example.imagesearch.presentation.common.DefaultDispatcherProvider
import com.example.imagesearch.presentation.common.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

}