package com.app.di.module.main

import com.app.network.main.MainApi
import com.app.ui.main.MainAdapter
import com.app.ui.main.MainDataSourceClass
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

        @MainScope
        @Provides
        @JvmStatic
        fun provideMainAdapter() = MainAdapter()

        @MainScope
        @Provides
        @JvmStatic
        fun provideMenRepository(mainApi: MainApi) = MainDataSourceClass(mainApi)
    }
}