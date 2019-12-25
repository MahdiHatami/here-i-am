package com.tuga.konum.domain.service

import android.content.Intent
import android.location.Location
import android.os.IBinder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.tuga.konum.util.NotificationHelper
import dagger.android.DaggerService
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class TrackingService : DaggerService() {

  @Inject
  lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var locationCallback: LocationCallback

  override fun onBind(intent: Intent?): IBinder? {
    throw UnsupportedOperationException("Not yet implemented")
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    return START_STICKY
  }

  companion object {
    var lastLocation: PublishSubject<Location> = PublishSubject.create()
  }

  var companion = Companion

  fun createLocationRequest(): LocationRequest? {
    return LocationRequest.create()?.apply {
      interval = 10000
      fastestInterval = 100
      smallestDisplacement = 1F
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
  }

  override fun onCreate() {
    super.onCreate()

    Timber.d("service Started")

    startLocationCallback()
  }

  private fun startLocationCallback() {
    locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        locationResult ?: return
        for (location in locationResult.locations) {
          lastLocation.onNext(location)
        }
      }
    }

    try {
      fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback, null)
    } catch (exception: SecurityException) {

    }

    // Start foreground service.
    startForeground(101, NotificationHelper(this).getBuilder().build())
  }

  override fun onDestroy() {
    super.onDestroy()
    fusedLocationClient.removeLocationUpdates(locationCallback)
  }
}