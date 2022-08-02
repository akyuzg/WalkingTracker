package com.akyuzg.walkingtracker.domain.repository

import com.akyuzg.walkingtracker.domain.model.Photo
import com.akyuzg.walkingtracker.utils.Result
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun collectNearestUniquePhoto(latitude: Double, longitude: Double): Result<Photo>

    suspend fun getAllPhotos(): Flow<List<Photo>>

}