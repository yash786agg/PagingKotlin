package com.app.di.module

import com.app.network.main.MainApi
import com.app.ui.main.MainAdapter
import com.app.ui.main.MainDataSourceClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object MainModule
{
    @Provides
    @ActivityRetainedScoped
    fun provideMainApi(retrofit: Retrofit) : MainApi = retrofit.create(MainApi::class.java)

    @Provides
    @ActivityRetainedScoped
    fun provideMainAdapter() = MainAdapter()

    @Provides
    @ActivityRetainedScoped
    fun provideMenRepository(mainApi: MainApi) = MainDataSourceClass(mainApi)
}