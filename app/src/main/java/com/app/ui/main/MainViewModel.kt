package com.app.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.model.main.PhotoListModel
import com.app.network.main.MainApi
import javax.inject.Inject

class MainViewModel @Inject constructor(app : Application) : AndroidViewModel(app)
{
    var postsLiveData : LiveData<PagedList<PhotoListModel>>

    init
    {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData = initializedPagedListBuilder(config).build()
    }

    private lateinit var mainApi: MainApi

    @Inject
    fun MainViewModel(mainApi: MainApi)
    {
        this.mainApi = mainApi
    }

    fun getPosts():LiveData<PagedList<PhotoListModel>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, PhotoListModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, PhotoListModel>() {
            override fun create(): MainDataSourceClass {
                return MainDataSourceClass(mainApi)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    /*private var listLiveData: LiveData<PagedList<PhotoListModel>>? = null
    private var mainDataSourceFactory : MainDataSourceFactory? = null
    private lateinit var mainRepository: MainRepository

    private val TAG : String = "MainActivity"

    @Inject
    fun MainViewModel(mainRepository: MainRepository)
    {
        this.mainRepository = mainRepository

        mainDataSourceFactory = MainDataSourceFactory(mainRepository)

        Log.e(TAG, "MainViewModel mainDataSourceFactory: $mainDataSourceFactory")

        initializePaging()
    }

    private fun initializePaging()
    {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(20).build()

        listLiveData = LivePagedListBuilder(mainDataSourceFactory!!, pagedListConfig).build()

        Log.e(TAG, "MainViewModel listLiveData: $listLiveData")
    }

    fun getListLiveData(): LiveData<PagedList<PhotoListModel>>
    {
        return listLiveData!!
    }*/


}