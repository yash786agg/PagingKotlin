package com.app.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.model.main.PhotoListModel
import com.app.network.main.MainApi
import com.app.util.NetworkState
import javax.inject.Inject

class MainViewModel @Inject constructor(app : Application) : AndroidViewModel(app)
{
    // FOR DATA ---
    var postsLiveData : LiveData<PagedList<PhotoListModel>>
    val data = MutableLiveData<MainDataSourceClass>()

    // OBSERVABLES ---
    val networkState: LiveData<NetworkState>? = switchMap(data) { it.getNetworkState() }

    // UTILS ---

    init
    {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(true)
            .build()
        postsLiveData = initializedPagedListBuilder(config).build()
    }

    private lateinit var mainApi: MainApi

    @Inject
    fun MainViewModel(mainApi: MainApi) {
        this.mainApi = mainApi
    }

    /**
     * Fetch a list of Photo [id,title] by PhotoListModel
     */

    fun getPosts():LiveData<PagedList<PhotoListModel>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, PhotoListModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, PhotoListModel>() {
            override fun create(): MainDataSourceClass {
                val source = MainDataSourceClass(mainApi)
                data.postValue(source)
                return source
            }
        }

        return LivePagedListBuilder(dataSourceFactory, config)
    }
}