package com.akyuzg.walkingtracker.data.network.response

import com.akyuzg.walkingtracker.data.network.model.PhotosPageJson
import com.google.gson.annotations.SerializedName

data class SearchPhotoResponse(

    @SerializedName(value = "photos")
    val photoPage: PhotosPageJson
)


