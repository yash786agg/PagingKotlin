package com.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.app.galleryimage.BuildConfig
import com.app.model.main.PhotoListModel
import com.app.network.main.MainApi
import com.app.util.Constants.Companion.flickrPhotosSearch
import com.app.util.Constants.Companion.format
import com.app.util.Constants.Companion.kittenSearch
import com.app.util.Constants.Companion.noJsonCallback
import com.app.util.Constants.Companion.perPage
import com.app.util.NetworkState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainDataSourceClass @Inject constructor(private val mainApi: MainApi) : PageKeyedDataSource<Int, PhotoListModel>()
{
    // FOR DATA ---
    private val networkState = MutableLiveData<NetworkState<String>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoListModel>)
    {
        networkState.postValue(NetworkState.Loading())

        GlobalScope.launch {
            try
            {
                val response = mainApi.fetchImageDataAsync(flickrPhotosSearch, BuildConfig.API_Key,kittenSearch,1,
                perPage, format, noJsonCallback).await()

                when {
                    response.isSuccessful ->
                    {
                        networkState.postValue(NetworkState.Success())

                        if(response.body() != null) {
                            val items = response.body()?.photos?.photo

                            if(items != null)
                                callback.onResult(items,0,perPage,null,2)
                            else
                                networkState.postValue(NetworkState.Error(response.errorBody().toString()))
                        }
                    }
                }
            }
            catch (exception : Exception) {
                networkState.postValue(NetworkState.Error(exception.message.toString()))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoListModel>)
    {
        networkState.postValue(NetworkState.Loading())

        GlobalScope.launch {
            try
            {
                val response = mainApi.fetchImageDataAsync(flickrPhotosSearch, BuildConfig.API_Key,kittenSearch,params.key,
                    perPage, format, noJsonCallback).await()
                when
                {
                    response.isSuccessful ->
                    {
                        networkState.postValue(NetworkState.Success())
                        val nextKey = (if(params.key == response.body()!!.photos.pages) null else params.key + 1)

                        if(response.body() != null) {
                            val items = response.body()?.photos?.photo

                            if(items != null)
                                callback.onResult(items, nextKey)
                            else
                                networkState.postValue(NetworkState.Error(response.errorBody().toString()))
                        }
                    }
                }

            }catch (exception : Exception) {
                networkState.postValue(NetworkState.Error(exception.message.toString()))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoListModel>) {}

    fun getNetworkState(): LiveData<NetworkState<String>> = networkState
}
