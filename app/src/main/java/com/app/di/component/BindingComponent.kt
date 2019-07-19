package com.app.di.component

import androidx.databinding.DataBindingComponent
import com.app.di.module.BindingModule
import com.app.di.module.DataBinding
import com.app.ui.main.MainBindingAdapter
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

/*@DataBinding
@Component(dependencies = [AppComponent::class], modules = [BindingModule::class])
interface BindingComponent: DataBindingComponent*/

/*@DataBinding
@Component(modules = [BindingModule::class])
interface BindingComponent : DataBindingComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun fresco(fresco: PipelineDraweeControllerBuilder): Builder
        fun bindingModule(bindingModule: BindingModule): Builder
        fun build(): BindingComponent
    }


    override fun getMainBindingAdapter(): MainBindingAdapter
}*/

/*@DataBinding
@Subcomponent(modules = [BindingModule::class])
interface BindingSubcomponent : DataBindingComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun fresco(fresco: PipelineDraweeControllerBuilder): Builder
        fun bindingModule(bindingModule: BindingModule): Builder
        fun build(): BindingSubcomponent
    }

    override fun getMainBindingAdapter(): MainBindingAdapter // Workaround for https://github.com/google/dagger/issues/665
}*/
