package com.app.di.module

import com.app.di.annotations.DataBinding
import com.app.network.main.ImageBindingApi
import com.app.ui.main.MainBindingAdapter
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object BindingModule
{
    @Provides
    @DataBinding
    fun provideBindingApi(retrofit: Retrofit) : ImageBindingApi = retrofit.create(ImageBindingApi::class.java)

    @Provides
    @DataBinding
    fun provideMainBindingAdapter(fresco: PipelineDraweeControllerBuilder, imageBindingApi: ImageBindingApi) : MainBindingAdapter = MainBindingAdapter(fresco,imageBindingApi)
}