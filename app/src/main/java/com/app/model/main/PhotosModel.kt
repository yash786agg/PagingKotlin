package com.app.model.main

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotosModel(@SerializedName("page") val page :Int?,
                       @SerializedName("pages") val pages :Int?,
                       @SerializedName("perpage") val perpage :Int?,
                       @SerializedName("photo") val photo :List<PhotoListModel>?) : Parcelable