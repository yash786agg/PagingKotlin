package com.app.model.main

import com.google.gson.annotations.SerializedName

data class LargeSizePhoto(@SerializedName("label") val label :String,
                          @SerializedName("source") val source :String)