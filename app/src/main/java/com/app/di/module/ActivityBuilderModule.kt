package com.app.di.module

import com.app.di.module.main.MainModule
import com.app.di.module.main.MainScope
import com.app.di.module.main.MainViewModelsModule
import com.app.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainViewModelsModule::class, MainModule::class])
    abstract fun getMainActivity(): MainActivity
}