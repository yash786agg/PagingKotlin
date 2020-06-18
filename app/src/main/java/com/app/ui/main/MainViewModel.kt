package com.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.*
import com.app.model.main.PhotoListModel
import com.app.util.BaseViewModel
import com.app.util.NetworkState
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val mainDataSourceClass : MainDataSourceClass) : BaseViewModel()
{
    // FOR DATA ---
    //var imgsLiveData : LiveData<PagedList<PhotoListModel>>
    val data = MutableLiveData<MainDataSourceClass>()

    val imgsLiveData = Pager(PagingConfig(pageSize = 20),pagingSourceFactory = {mainDataSourceClass}).flow.cachedIn(ioScope)

    // OBSERVABLES ---
    val networkState: LiveData<NetworkState<String>>? = switchMap(data) { it.getNetworkState() }

    // UTILS ---
    /*init
    {
        val config = PagedList.Config.Builder().setPageSize(20)
            .setEnablePlaceholders(true).build()

        imgsLiveData = initializedPagedListBuilder(config).build()
    }*/

    /**
     * Fetch a list of Photo [id,title] by PhotoListModel
     */

    //fun getData() : LiveData<PagedList<PhotoListModel>> = imgsLiveData

   /* private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, PhotoListModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, PhotoListModel>() {
            override fun create(): MainDataSourceClass {
                data.postValue(mainDataSourceClass)
                return mainDataSourceClass
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }*/
}