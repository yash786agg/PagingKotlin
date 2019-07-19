package com.app.ui.main

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.galleryimage.R
import com.app.model.main.PhotoListModel
import com.app.util.DiffUtilCallBack
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.request.ImageRequest
import kotlinx.android.synthetic.main.main_adapter.view.*

class MainAdapter : PagedListAdapter<PhotoListModel, MainAdapter.MyViewHolder>(DiffUtilCallBack())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageId = itemView.imageView
        private val TAG : String = "MainActivity"
        fun bindPost(redditPost : PhotoListModel){
            with(redditPost){
                //titleText.text = title

                //https://farm6.staticflickr.com/5800/31456463045_5a0af4ddc8_q.jpg

                val draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri(Uri.parse("https://live.staticflickr.com/5800/31456463045_5a0af4ddc8_q.jpg")))
                    .setOldController(itemView.imageView.controller).build()
                itemView.imageView.controller = draweeController

                Log.e(TAG, "loadImageData title: "+title)
                Log.e(TAG, "loadImageData id: "+id)
            }
        }
    }
}