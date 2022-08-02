package com.akyuzg.walkingtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akyuzg.walkingtracker.data.database.model.PhotoEntity

@Database(
    entities = [PhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PhotoDatabase: RoomDatabase() {

    abstract val imageDao: PhotoDao

    companion object  {
        const val DATABASE_NAME = "db"
    }
}