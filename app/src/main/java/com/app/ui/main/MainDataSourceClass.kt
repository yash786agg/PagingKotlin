package com.app.ui.main

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.app.galleryimage.BuildConfig
import com.app.model.main.PhotoListModel
import com.app.network.main.MainApi
import com.app.util.Constants.Companion.flickrPhotosSearch
import com.app.util.Constants.Companion.format
import com.app.util.Constants.Companion.kittenSearch
import com.app.util.Constants.Companion.noJsonCallback
import com.app.util.Constants.Companion.perPage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//https://github.com/wangerekaharun/AndroidPagingWithCoroutines

class MainDataSourceClass constructor(private var mainApi: MainApi) : PageKeyedDataSource<Int, PhotoListModel>()
{
    private val TAG : String = "MainActivity"

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoListModel>)
    {
        GlobalScope.launch {
            try
            {
                val response = mainApi.fetchImageData(flickrPhotosSearch, BuildConfig.API_Key,kittenSearch,1,
                perPage, format, noJsonCallback).await()
                when
                {
                    response.isSuccessful -> {
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
                Log.e(TAG, "Failed to fetch loadInitial!: "+exception.message)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoListModel>)
    {
        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize)

        GlobalScope.launch {
            try
            {
                val response = mainApi.fetchImageData(flickrPhotosSearch, BuildConfig.API_Key,kittenSearch,params.key,
                    perPage, format, noJsonCallback).await()
                when
                {
                    response.isSuccessful ->
                    {
                        val nextKey = (if(params.key == response.body()!!.photos.pages) null else params.key + 1)
                        val listing = response.body()?.photos
                        val items = listing?.photo

                        Log.e(TAG, "loadAfter redditPostsitial: "+items!!)
                        Log.e(TAG, "loadAfter redditPostsitial size: "+items.size)

                        callback.onResult(items ?: listOf(), nextKey)
                    }
                }

            }catch (exception : Exception){
                Log.e(TAG, "Failed to fetch loadAfter!")
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoListModel>) {}

    /*private val TAG : String = "MainActivity"

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoListModel>)
    {
        Log.e(TAG, "MainDataSourceClass loadInitial")
        GlobalScope.launch(Dispatchers.Default)
        {
            val response = mainRepository.imageData(1)
            Log.e(TAG, "MainDataSourceClass loadInitial response: "+response.photos.photo[0].id)


            withContext(Dispatchers.Main) {
                // Perform operations on the main thread
                //imageData!!.value = NetworkResource.Success(response)

                Log.e(TAG, "MainDataSourceClass loadInitial response inside: "+response.photos.photo)

                callback.onResult(response.photos.photo,null,2)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoListModel>) {
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoListModel>)
    {
        GlobalScope.launch(Dispatchers.Default)
        {
            val response = mainRepository.imageData(params.key)

            Log.e(TAG, "MainDataSourceClass loadBefore response: "+response.photos.photo[0].id)

            withContext(Dispatchers.Main) {
                // Perform operations on the main thread
                //imageData!!.value = NetworkResource.Success(response)

                callback.onResult(response.photos.photo, params.key + 1)

                Log.e(TAG, "MainDataSourceClass loadBefore response inside: "+response.photos.photo)
            }
        }
    }*/

}
