package com.example.marvelappstarter.data.model.comic

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicModelResponse(

    @SerializedName("results")
    val data : ComicModelData

) : Serializable
