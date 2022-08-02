package com.akyuzg.walkingtracker.data.network

import com.akyuzg.walkingtracker.data.network.response.SearchPhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {

    @GET("services/rest/?method=flickr.photos.search&nojsoncallback=1")
    suspend fun searchPhoto(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ) : Response<SearchPhotoResponse>


    companion object {
        const val API_KEY = "ee0e13da7cdfb637bd43fa4a8fe83297"
        const val BASE_URL = "https://api.flickr.com/"
        const val DEFAULT_FORMAT = "json"
    }
}