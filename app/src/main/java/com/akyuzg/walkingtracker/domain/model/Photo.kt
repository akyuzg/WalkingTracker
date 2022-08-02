package com.akyuzg.walkingtracker.domain.model

import com.akyuzg.walkingtracker.ui.model.PhotoItemView

data class Photo(
    val id: String,
    val secret: String,
    val server: String
)

fun Photo.toUiModel() = PhotoItemView(
    id = id,
    secret = secret,
    server = server
)