package com.app.model.main

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sizes(@SerializedName("size") val size :List<LargeSizePhoto>) : Parcelable