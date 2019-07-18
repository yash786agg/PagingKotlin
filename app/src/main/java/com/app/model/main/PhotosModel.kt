package com.app.model.main

import com.google.gson.annotations.SerializedName

data class PhotosModel(@SerializedName("page") val page :Int,
                       @SerializedName("pages") val pages :Int,
                       @SerializedName("perpage") val perpage :Int,
                       @SerializedName("photo") val photo :List<PhotoListModel>)