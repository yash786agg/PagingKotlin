package com.app.di.module

import com.app.galleryimage.BuildConfig
import com.app.util.Constants.Companion.TIMEOUT_REQUEST
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule
{
    @Module
    companion object
    {
       /* @JvmStatic
        @Singleton
        @Provides
        fun providesDraweeController() : PipelineDraweeControllerBuilder = Fresco.newDraweeControllerBuilder()*/

        @JvmStatic
        @Singleton
        @Provides
        fun retrofitInstance(): Retrofit
        {
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }
    }
}