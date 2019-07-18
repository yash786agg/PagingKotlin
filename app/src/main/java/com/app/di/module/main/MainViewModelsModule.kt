package com.app.di.module.main

import androidx.lifecycle.ViewModel
import com.app.di.annotations.ViewModelKey
import com.app.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule
{
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindPostViewModel(viewModel: MainViewModel): ViewModel
}