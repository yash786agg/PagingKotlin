package com.app.util

import androidx.recyclerview.widget.DiffUtil
import com.app.model.main.PhotoListModel

class DiffUtilCallBack : DiffUtil.ItemCallback<PhotoListModel>() {
    override fun areItemsTheSame(oldItem: PhotoListModel, newItem: PhotoListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoListModel, newItem: PhotoListModel): Boolean {
        return oldItem == newItem
    }
}