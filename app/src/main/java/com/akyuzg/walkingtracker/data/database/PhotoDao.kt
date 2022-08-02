package com.akyuzg.walkingtracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akyuzg.walkingtracker.data.database.model.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: PhotoEntity)

    fun insertWithTimestamp(photo: PhotoEntity) {
        insert(photo.apply{
            createdAt = System.currentTimeMillis()
        })
    }

    @Query("SELECT * FROM images ORDER BY created_at DESC")
    fun getAllPhotoEntities(): Flow<List<PhotoEntity>>

}