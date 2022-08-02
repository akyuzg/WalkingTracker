package com.akyuzg.walkingtracker.ui.model

data class PhotoItemView(
    val id: String,
    val secret: String,
    val server: String
)

fun PhotoItemView.toUrl(): String {
    return "https://live.staticflickr.com/${server}/${id}_${secret}_w.jpg"
}