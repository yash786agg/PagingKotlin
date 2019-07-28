package com.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.model.main.PhotoListModel
import com.app.util.NetworkState
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainDataSourceClass : MainDataSourceClass) : ViewModel()
{
    // FOR DATA ---
    var imgsLiveData : LiveData<PagedList<PhotoListModel>>
    val data = MutableLiveData<MainDataSourceClass>()

    // OBSERVABLES ---
    val networkState: LiveData<NetworkState<String>>? = switchMap(data) { it.getNetworkState() }

    // UTILS ---
    init
    {
        val config = PagedList.Config.Builder().setPageSize(20)
            .setEnablePlaceholders(true).build()

        imgsLiveData = initializedPagedListBuilder(config).build()
    }

    /**
     * Fetch a list of Photo [id,title] by PhotoListModel
     */

    fun getData() : LiveData<PagedList<PhotoListModel>> = imgsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, PhotoListModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, PhotoListModel>() {
            override fun create(): MainDataSourceClass {
                data.postValue(mainDataSourceClass)
                return mainDataSourceClass
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }
}