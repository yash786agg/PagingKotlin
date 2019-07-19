package com.app.ui.main

import android.util.Log
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

class MainDataSourceClass constructor(private var mainApi: MainApi) : PageKeyedDataSource<Int, PhotoListModel>()
{
    // FOR DATA ---
    private val networkState = MutableLiveData<NetworkState>()

    private val TAG : String = "MainActivity"

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoListModel>)
    {
        networkState.postValue(NetworkState.LOADING)

        GlobalScope.launch {
            try
            {
                val response = mainApi.fetchImageData(flickrPhotosSearch, BuildConfig.API_Key,kittenSearch,1,
                perPage, format, noJsonCallback).await()
                when
                {
                    response.isSuccessful -> {
                        networkState.postValue(NetworkState.SUCCESS)
                        val listing = response.body()?.photos
                        val items = listing?.photo
                        Log.e(TAG, "loadInitial redditPostsitial: "+items!!)
                        Log.e(TAG, "loadInitial redditPostsitial size: "+items.size)
                        callback.onResult(items ?: listOf(),null,2)
                    }
                }

            }
            catch (exception : Exception)
            {
                networkState.postValue(NetworkState.FAILED)
                Log.e(TAG, "Failed to fetch loadInitial!: "+exception.message)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoListModel>)
    {
        Log.e(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize)

        networkState.postValue(NetworkState.LOADING)

        GlobalScope.launch {
            try
            {
                val response = mainApi.fetchImageData(flickrPhotosSearch, BuildConfig.API_Key,kittenSearch,params.key,
                    perPage, format, noJsonCallback).await()
                when
                {
                    response.isSuccessful ->
                    {
                        networkState.postValue(NetworkState.SUCCESS)
                        val nextKey = (if(params.key == response.body()!!.photos.pages) null else params.key + 1)
                        val listing = response.body()?.photos
                        val items = listing?.photo

                        Log.e(TAG, "loadAfter redditPostsitial: "+items!!)
                        Log.e(TAG, "loadAfter redditPostsitial size: "+items.size)

                        callback.onResult(items, nextKey)
                    }
                }

            }catch (exception : Exception){
                networkState.postValue(NetworkState.FAILED)
                Log.e(TAG, "Failed to fetch loadAfter!")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoListModel>) {}

    fun getNetworkState(): LiveData<NetworkState> = networkState
}
