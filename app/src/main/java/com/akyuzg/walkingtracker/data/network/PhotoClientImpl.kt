package com.akyuzg.walkingtracker.data.network

import com.akyuzg.walkingtracker.data.network.model.PhotoJson
import com.akyuzg.walkingtracker.utils.Result
import javax.inject.Inject

class PhotoClientImpl @Inject constructor(
    private val photosService: PhotoService
) : PhotoClient {

    override suspend fun findNearestPhoto(latitude: Double, longitude: Double): Result<PhotoJson> {
        val response = photosService.searchPhoto(latitude, longitude)

        return try {
            val photoJson: PhotoJson = response.body()?.photoPage?.items?.get(0)!!
            Result.Success(photoJson)
        }catch (e: Exception){
            Result.Error("photo not found exception")
        }
    }

}