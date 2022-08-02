package com.akyuzg.walkingtracker.data.network

import com.akyuzg.walkingtracker.data.network.model.PhotoJson
import com.akyuzg.walkingtracker.utils.Result

interface PhotoClient {
    suspend fun findNearestPhoto(latitude: Double, longitude: Double): Result<PhotoJson>
}