package com.akyuzg.walkingtracker.data.network.model

import com.akyuzg.walkingtracker.data.database.model.PhotoEntity
import com.akyuzg.walkingtracker.domain.model.Photo

data class PhotoJson(
    val id: String,
    val secret: String,
    val server: String
)

fun PhotoJson.toPhoto() = Photo(
    id = id,
    secret = secret,
    server = server,
)

fun PhotoJson.toPhotoEntity() = PhotoEntity(
    id = id,
    secret = secret,
    server = server,
)
