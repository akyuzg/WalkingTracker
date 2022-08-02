package com.akyuzg.walkingtracker.ui

import androidx.lifecycle.*
import com.akyuzg.walkingtracker.data.LocationManager
import com.akyuzg.walkingtracker.domain.model.toUiModel
import com.akyuzg.walkingtracker.domain.repository.PhotoRepository
import com.akyuzg.walkingtracker.ui.model.PhotoItemView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val locationManager: LocationManager
) : ViewModel() {

    private val _subscriptionActive = MutableLiveData<Boolean>()
    val subscriptionActive: LiveData<Boolean> get() = _subscriptionActive

     suspend fun fetchPhotos(): Flow<List<PhotoItemView>>  {
         return repository.getAllPhotos().mapLatest { photos ->
             photos.map { it.toUiModel() }
         }
    }

    fun startLocationUpdates(){
        locationManager.startLocationUpdates()
        _subscriptionActive.value = locationManager.isSubscribed()
    }

    fun stopLocationUpdates(){
        locationManager.stopLocationUpdates()
        _subscriptionActive.value = locationManager.isSubscribed()
    }

    fun isLocationServiceActive(): Boolean {
        return locationManager.isSubscribed()
    }

}