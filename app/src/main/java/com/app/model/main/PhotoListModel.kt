package com.app.model.main

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoListModel(@SerializedName("id") val id :String?,
                          @SerializedName("title") val title :String?) : Parcelable