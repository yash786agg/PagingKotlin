package com.app.di.component

import android.app.Application
import javax.inject.Singleton
import com.app.di.module.ActivityBuilderModule
import com.app.di.module.AppModule
import com.app.di.module.ViewModelFactoryModule
import com.app.galleryimage.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilderModule::class, AppModule::class,ViewModelFactoryModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
