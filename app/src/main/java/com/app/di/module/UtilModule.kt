package com.app.di.module

import android.app.Application
import com.app.util.UiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object UtilModule
{
    @Provides
    fun uiHelper(application: Application) = UiHelper(application)
}