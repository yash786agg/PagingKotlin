package com.app.di.module.main

import com.app.network.main.MainApi
import com.app.ui.main.MainRepository
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
        fun provideMainApi(retrofit: Retrofit) : MainApi
        {
            return retrofit.create(MainApi::class.java)
        }

        @JvmStatic
        @MainScope
        @Provides
        fun provideMainRepository(mainApi: MainApi): MainRepository {
            return MainRepository(mainApi)
        }
    }
}