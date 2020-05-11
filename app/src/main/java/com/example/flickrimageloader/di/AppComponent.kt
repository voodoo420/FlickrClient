package com.example.flickrimageloader.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent{
    fun createActivityComponent(): ActivityComponent.Builder
}