package com.app.model.main

import com.google.gson.annotations.SerializedName

data class PhotoListModel(@SerializedName("id") val id :String,
                          @SerializedName("title") val title :String)