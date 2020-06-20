package com.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.app.util.Constants.Companion.perPage

class MainViewModel @ViewModelInject constructor(private val mainDataSourceClass : MainDataSourceClass) : ViewModel()
{
    /* // FOR Kotlin Flow ---
     private var currentResult: Flow<PagingData<PhotoListModel>>? = null

     // OBSERVABLES ---
     fun imageData(): Flow<PagingData<PhotoListModel>> {
         val lastResult = currentResult
         if (lastResult != null) return lastResult

         val newResult: Flow<PagingData<PhotoListModel>> = Pager(PagingConfig(pageSize = perPage),pagingSourceFactory = {mainDataSourceClass}).flow.cachedIn(viewModelScope)
         currentResult = newResult
         return newResult
     }*/

    // FOR Kotlin Coroutines ---
    val imageData = Pager(PagingConfig(pageSize = perPage),pagingSourceFactory = {mainDataSourceClass}).flow.cachedIn(viewModelScope)
}