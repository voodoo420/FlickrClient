package com.example.flickrimageloader

import android.app.Application
import com.example.flickrimageloader.di.AppComponent
import com.example.flickrimageloader.di.DaggerAppComponent
import timber.log.Timber

class App : Application(){

    companion object{ lateinit var appComponent: AppComponent }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        Timber.plant(Timber.DebugTree())
    }
}