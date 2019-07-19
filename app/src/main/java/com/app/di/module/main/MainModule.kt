package com.app.di.module.main

import com.app.network.main.MainApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule
{
    @Module
    companion object
    {
        @MainScope
        @Provides
        @JvmStatic
        fun provideMainApi(retrofit: Retrofit) : MainApi = retrofit.create(MainApi::class.java)
    }
}