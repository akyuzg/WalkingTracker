package com.akyuzg.walkingtracker.data.repository

import com.akyuzg.walkingtracker.data.database.PhotoDao
import com.akyuzg.walkingtracker.data.database.model.toDomainModel
import com.akyuzg.walkingtracker.data.network.PhotoClient
import com.akyuzg.walkingtracker.data.network.model.toPhoto
import com.akyuzg.walkingtracker.data.network.model.toPhotoEntity
import com.akyuzg.walkingtracker.domain.model.Photo
import com.akyuzg.walkingtracker.domain.repository.PhotoRepository
import com.akyuzg.walkingtracker.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoClient: PhotoClient,
    private val photoDao: PhotoDao
) : PhotoRepository {

    /*
         In photos table which has three column as a primary
         So it compares these three values to determine the image which is received from the server is already in our database.
         We don't need to search the photo data in the database manually.
     */
    override suspend fun collectNearestUniquePhoto(latitude: Double, longitude: Double): Result<Photo> {
        return when (val response = photoClient.findNearestPhoto(latitude, longitude)) {
            is Result.Success -> {
                val photoEntity = response.data.toPhotoEntity()
                photoDao.insertWithTimestamp(photoEntity)
                Result.Success(response.data.toPhoto())
            } else -> {
                Result.Error("unknown exception")
            }
        }
    }

    override suspend fun getAllPhotos(): Flow<List<Photo>> {
        return photoDao.getAllPhotoEntities().mapLatest { photos ->
            photos.map { it.toDomainModel() }
        }
    }

}