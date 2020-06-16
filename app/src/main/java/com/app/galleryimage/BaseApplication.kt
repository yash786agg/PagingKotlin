package com.app.galleryimage

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.app.di.component.DaggerAppComponent
import com.app.di.module.BindingModule
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        val bindingComponent = DaggerAppComponent.builder()
            .bindingModule(BindingModule)
            .application(this)
            .fresco(Fresco.newDraweeControllerBuilder())
            .build()

        DataBindingUtil.setDefaultComponent(bindingComponent)
    }

    override fun onLowMemory()
    {
        super.onLowMemory()
        val imagePipeline = Fresco.getImagePipeline()
        imagePipeline.clearCaches()
    }
}