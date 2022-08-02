package com.akyuzg.walkingtracker.data

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.akyuzg.walkingtracker.domain.repository.PhotoRepository
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationService @Inject constructor() : Service() {

    @Inject lateinit var repository: PhotoRepository

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            createNotificationChanel()
        else
            startForeground(1, Notification())

        val noPermissionsGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED

        if (noPermissionsGranted) {
            Toast.makeText(applicationContext, "Permission required", Toast.LENGTH_LONG).show()
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback, Looper.getMainLooper()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel() {
        val notificationChannelId = "Location channel id"
        val channelName = "location background"
        val channel = NotificationChannel(
            notificationChannelId,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(channel)
        val notificationBuilder =
            NotificationCompat.Builder(this, notificationChannelId)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setContentTitle("Location")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(2, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private val locationRequest: LocationRequest = create().apply {
        interval = LOCATION_REQUEST_INTERVAL
        fastestInterval = LOCATION_REQUEST_INTERVAL / 2
        smallestDisplacement = DISPLACEMENT_THRESHOLD
        priority = Priority.PRIORITY_HIGH_ACCURACY
    }

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location = locationList.last()
                Toast.makeText(this@LocationService, "location received", Toast.LENGTH_LONG).show()
                scope.launch {
                    repository.collectNearestUniquePhoto(location.latitude, location.longitude)
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val LOCATION_REQUEST_INTERVAL = 3 * 1000L
        const val DISPLACEMENT_THRESHOLD = 100F // in meter
    }
}