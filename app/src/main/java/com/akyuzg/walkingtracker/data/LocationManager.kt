package com.akyuzg.walkingtracker.data

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.akyuzg.walkingtracker.R
import com.akyuzg.walkingtracker.utils.LocationServiceStatus
import com.akyuzg.walkingtracker.utils.PreferencesKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    var mLocationService: LocationService = LocationService()
    lateinit var mServiceIntent: Intent

    fun startLocationUpdates() {
        mLocationService = LocationService()
        mServiceIntent = Intent(context, mLocationService.javaClass)
        context.startService(mServiceIntent)
        updateLocationServiceState(LocationServiceStatus.ACTIVE)
        Toast.makeText(
            context,
            context.getString(R.string.location_service_active_message),
            Toast.LENGTH_SHORT).show()
    }

    fun stopLocationUpdates() {
        mLocationService = LocationService()
        mServiceIntent = Intent(context, mLocationService.javaClass)
        context.stopService(mServiceIntent)
        updateLocationServiceState(LocationServiceStatus.INACTIVE)
        Toast.makeText(
            context,
            context.getString(R.string.location_service_stopped_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateLocationServiceState(enabled: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putBoolean(PreferencesKeys.LOCATION_STATUS, enabled)
            .apply()
    }

    fun isSubscribed(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(PreferencesKeys.LOCATION_STATUS, LocationServiceStatus.INACTIVE)
    }

}