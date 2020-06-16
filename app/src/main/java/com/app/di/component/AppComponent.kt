package com.app.di.component

import android.app.Application
import androidx.databinding.DataBindingComponent
import com.app.di.annotations.DataBinding
import com.app.di.module.AppModule
import com.app.di.module.BindingModule
import com.app.ui.main.MainBindingAdapter
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@DataBinding
@Component(modules = [
        AppModule::class,
        BindingModule::class
    ]
)
interface AppComponent : DataBindingComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

        @BindsInstance
        fun fresco(fresco: PipelineDraweeControllerBuilder): Builder
        fun bindingModule(bindingModule: BindingModule): Builder
    }

    override fun getMainBindingAdapter(): MainBindingAdapter
}