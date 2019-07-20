package com.app.model.main

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoSizes(@SerializedName("sizes") val sizes :Sizes) : Parcelable