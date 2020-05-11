package com.example.flickrimageloader.di

import com.example.flickrimageloader.activity.MainActivity
import com.example.flickrimageloader.presenter.PhotosView
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)

    @Subcomponent.Builder
    interface Builder {
        fun setModule(activityModule: ActivityModule): Builder
        fun build(): ActivityComponent
    }
}