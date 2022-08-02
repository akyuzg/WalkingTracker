package com.akyuzg.walkingtracker.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.akyuzg.walkingtracker.BuildConfig
import com.akyuzg.walkingtracker.data.LocationManager
import com.akyuzg.walkingtracker.data.database.PhotoDao
import com.akyuzg.walkingtracker.data.database.PhotoDatabase
import com.akyuzg.walkingtracker.data.network.PhotoService
import com.akyuzg.walkingtracker.data.network.MainInterceptor
import com.akyuzg.walkingtracker.data.network.PhotoClient
import com.akyuzg.walkingtracker.data.network.PhotoClientImpl
import com.akyuzg.walkingtracker.data.repository.PhotoRepositoryImpl
import com.akyuzg.walkingtracker.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): PhotoDatabase {
        return Room.databaseBuilder(
            app,
            PhotoDatabase::class.java,
            PhotoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePhotoService(): PhotoService {
        return Retrofit.Builder()
            .baseUrl(PhotoService.BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }


    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideHttpLoggingInterceptor())
            .addInterceptor(MainInterceptor())
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(db: PhotoDatabase): PhotoDao {
        return db.imageDao
    }


    @Provides
    @Singleton
    fun providePhotoClient(photoService: PhotoService): PhotoClient {
        return PhotoClientImpl(photoService)
    }


    @Provides
    @Singleton
    fun providePhotoRepository(photoClient: PhotoClient, db: PhotoDatabase): PhotoRepository {
        return PhotoRepositoryImpl(photoClient, db.imageDao)
    }

    @Provides
    @Singleton
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager {
        return LocationManager(context)
    }

}