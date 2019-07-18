package com.app.galleryimage

import com.app.di.component.DaggerAppComponent
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication()
{
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
    {
        Fresco.initialize(this)

        return DaggerAppComponent.builder().application(this).build()
    }
}