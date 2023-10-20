package com.example.marvelappstarter.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ThumbnailModel(
    @SerializedName("path")
    val path : String,
    @SerializedName("extesion")
    val extesion : String


) : Serializable {
}