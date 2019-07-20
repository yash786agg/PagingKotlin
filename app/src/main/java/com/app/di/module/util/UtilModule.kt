package com.app.di.module.util

import android.app.Application
import com.app.util.UiHelper
import dagger.Module
import dagger.Provides

@Module
class UtilModule
{
    @Module
    companion object
    {
        @Provides
        @JvmStatic
        fun uiHelper(application: Application) = UiHelper(application)
    }
}