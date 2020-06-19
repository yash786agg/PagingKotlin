package com.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.app.util.BaseViewModel
import com.app.util.Constants.Companion.perPage

class MainViewModel @ViewModelInject constructor(private val mainDataSourceClass : MainDataSourceClass) : BaseViewModel()
{
    // FOR DATA ---
    val data = MutableLiveData<MainDataSourceClass>()

    // OBSERVABLES ---
    val imgsLiveData = Pager(PagingConfig(pageSize = perPage),pagingSourceFactory = {mainDataSourceClass}).flow.cachedIn(ioScope)
}