package com.app.ui.main

import android.net.Uri
import android.util.Log
import androidx.databinding.BindingAdapter
import com.app.galleryimage.BuildConfig
import com.app.model.main.PhotoListModel
import com.app.network.main.ImageBindingApi
import com.app.util.Constants.Companion.flickrPhotosGetSizes
import com.app.util.Constants.Companion.format
import com.app.util.Constants.Companion.noJsonCallback
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainBindingAdapter(fresco: PipelineDraweeControllerBuilder,imageBindingApi: ImageBindingApi)
{
    private val TAG : String = "MainBindingAdapter"
    private var fresco : PipelineDraweeControllerBuilder? = null
    private var imageBindingApi: ImageBindingApi? = null

    init {
        this.fresco = fresco
        this.imageBindingApi = imageBindingApi
    }

    @BindingAdapter("photoItemImage")
    fun newsItemImage(imageView: SimpleDraweeView, photoList: PhotoListModel)
    {
        Log.e(TAG,"MainBindingAdapter id: "+photoList.id)
        Log.e(TAG, "MainBindingAdapter fresco: $fresco")
        Log.e(TAG, "MainBindingAdapter imageBindingApi: $imageBindingApi")

        GlobalScope.launch {
            try
            {
                val response = imageBindingApi!!.fetchSingleImageAsync(flickrPhotosGetSizes, BuildConfig.API_Key,photoList.id ,format, noJsonCallback).await()

                withContext(Dispatchers.Main) {
                    // Perform operations on the main thread
                    when
                    {
                        response.isSuccessful -> {
                            Log.e(TAG, "MainBindingAdapter respons esize: ${response.body()!!.sizes.size.size}")
                            Log.e(TAG, "MainBindingAdapter response: ${response.body()!!.sizes.size[1].source}")

                            val imageUrl = response.body()!!.sizes.size[1].source
                            val draweeController = fresco!!.setImageRequest(ImageRequest.fromUri(Uri.parse(imageUrl)))
                                .setOldController(imageView.controller).build()
                            imageView.controller = draweeController
                        }
                    }
                }
            }
            catch (exception : Exception)
            {
                Log.e(TAG, "MainBindingAdapter Exception: "+exception.message)
            }
        }
    }
}