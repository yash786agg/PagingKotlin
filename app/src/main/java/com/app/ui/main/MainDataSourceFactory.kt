package com.app.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.app.model.main.PhotoListModel

/*class MainDataSourceFactory constructor(private var mainRepository: MainRepository) : DataSource.Factory<Int, PhotoListModel>()
{
    private var liveData: MutableLiveData<MainDataSourceClass> ? = null

    *//*fun getMutableLiveData(): MutableLiveData<MainDataSourceClass>
    {
        return liveData!!
    }*//*

    override fun create(): DataSource<Int, PhotoListModel>
    {
        liveData = MutableLiveData()
        val dataSourceClass = MainDataSourceClass(mainRepository)
        liveData!!.postValue(dataSourceClass)
        return dataSourceClass
    }
}*/

