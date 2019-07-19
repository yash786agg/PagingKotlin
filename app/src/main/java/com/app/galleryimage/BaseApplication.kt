package com.app.galleryimage

import androidx.databinding.DataBindingUtil
import com.app.di.component.DaggerAppComponent
import com.app.di.module.BindingModule
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class BaseApplication : DaggerApplication()
{
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
    {
        Fresco.initialize(this)

        //val appComponent = DaggerAppComponent.builder().application(this)

        val bindingComponent = DaggerAppComponent.builder()
            .bindingModule(BindingModule)
            .application(this)
            .fresco(Fresco.newDraweeControllerBuilder())
            .build()

        DataBindingUtil.setDefaultComponent(bindingComponent)

        return bindingComponent
        /*val appComponent = DaggerAppComponent.builder().application(this).build()

        val bindingSubComponent = appComponent.bindingSubcomponentBuilder()
            .bindingModule(BindingModule)
            .fresco(Fresco.newDraweeControllerBuilder())
            .build()
        DataBindingUtil.setDefaultComponent(bindingSubComponent)

        return appComponent*/
        //return DaggerAppComponent.builder().application(this).build()
    }
}