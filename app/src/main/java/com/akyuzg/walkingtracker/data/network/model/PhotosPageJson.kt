package com.akyuzg.walkingtracker.data.network.model

import com.google.gson.annotations.SerializedName


data class PhotosPageJson(
    @SerializedName(value = "photo")
    val items: List<PhotoJson>
)