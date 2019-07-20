package com.app.model.main

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LargeSizePhoto(@SerializedName("label") val label :String,
                          @SerializedName("source") val source :String) : Parcelable