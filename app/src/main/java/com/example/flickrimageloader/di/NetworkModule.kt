package com.example.flickrimageloader.di

import com.example.flickrimageloader.data.PhotosRemoteDataSource
import com.example.flickrimageloader.service.FlickrApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    fun provideApiService() = FlickrApiService.create()

    @Provides
    @Singleton
    fun provideDataSource(flickrApiService: FlickrApiService) = PhotosRemoteDataSource(flickrApiService)
}