package com.app.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.galleryimage.R
import com.app.model.main.PhotoListModel
import com.app.util.DiffUtilCallBack
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
        val titleText = itemView.title

        fun bindPost(redditPost : PhotoListModel){
            with(redditPost){
                titleText.text = title
            }
        }
    }
}