package com.app.ui.main

import android.util.Log
import com.app.galleryimage.BuildConfig
import com.app.model.main.MainModel
import com.app.network.main.MainApi
import com.app.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class MainRepository @Inject constructor(private var mainApi: MainApi)
{
    suspend fun imageData(pageValue : Int) : MainModel
    {
        var response : MainModel? = null
        //imageData!!.value = NetworkResource.Loading(null)

        //Do operations on some thread async
        try
        {
            /*response = mainApi.fetchImageData(Constants.flickrPhotosSearch, BuildConfig.API_Key, Constants.kittenSearch,pageValue,
              Constants.perPage, Constants.format, Constants.noJsonCallback).await()*/

            Log.e("MainActivity", "imageData Success: $response")

            /*withContext(Dispatchers.Main) {
              // Perform operations on the main thread
              //imageData!!.value = NetworkResource.Success(response)
            }*/
        }
        catch (e: HttpException)
        {
          e.printStackTrace()
          //imageData!!.value = NetworkResource.Error(e.code().toString(),null)
        }

        Log.e("MainActivity", "imageData Success outside: $response")

        return response!!
    }
}