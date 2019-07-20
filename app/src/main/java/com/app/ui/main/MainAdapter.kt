package com.app.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.galleryimage.R
import com.app.galleryimage.databinding.MainAdapterBinding
import com.app.model.main.PhotoListModel
import com.app.util.DiffUtilCallBack

class MainAdapter : PagedListAdapter<PhotoListModel, MainAdapter.MyViewHolder>(DiffUtilCallBack())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : MainAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
            , R.layout.main_adapter, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int): Int = position

    class MyViewHolder(private val binding : MainAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photoList : PhotoListModel) {
            binding.dataManager = photoList
        }
    }
}