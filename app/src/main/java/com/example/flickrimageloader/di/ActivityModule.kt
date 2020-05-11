package com.example.flickrimageloader.di

import com.example.flickrimageloader.data.PhotosRemoteDataSource
import com.example.flickrimageloader.presenter.PhotosPresenterImpl
import com.example.flickrimageloader.presenter.PhotosView
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val view: PhotosView) {

    @Provides
    fun providePresenter(dataSource: PhotosRemoteDataSource) = PhotosPresenterImpl(dataSource, view)
}