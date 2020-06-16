package com.app.ui.main

import android.net.Uri
import android.text.TextUtils
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
import javax.inject.Inject

class MainBindingAdapter @Inject constructor(private val fresco: PipelineDraweeControllerBuilder,
                                             private val imageBindingApi: ImageBindingApi)
{
    @BindingAdapter("photoItemImage")
    fun photoItemImage(imageView: SimpleDraweeView, photoList: PhotoListModel)
    {
        GlobalScope.launch {
            try
            {
                val response = imageBindingApi.fetchSingleImageAsync(flickrPhotosGetSizes, BuildConfig.API_Key,photoList.id ,format, noJsonCallback).await()

                withContext(Dispatchers.Main) {
                    // Perform operations on the main thread
                    when
                    {
                        response.isSuccessful -> {
                            if(response.body() != null)
                            {
                                if(!TextUtils.isEmpty(response.body()!!.sizes.size[1].source)) {
                                    val imageUrl = response.body()!!.sizes.size[1].source
                                    val draweeController = fresco.setImageRequest(ImageRequest.fromUri(Uri.parse(imageUrl)))
                                        .setOldController(imageView.controller).build()
                                    imageView.controller = draweeController
                                }
                            }
                        }
                    }
                }
            }
            catch (exception : Exception) {
                exception.printStackTrace()
            }
        }
    }
}