package com.akyuzg.walkingtracker.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.akyuzg.walkingtracker.domain.model.Photo

@Entity(tableName = "images", primaryKeys = ["id", "server", "secret"])
data class PhotoEntity(
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "server") val server: String,
    @ColumnInfo(name = "secret") val secret: String,
    @ColumnInfo(name = "created_at") var createdAt: Long = 0,
)

fun PhotoEntity.toDomainModel() = Photo(
    id = id,
    secret = secret,
    server = server
)
