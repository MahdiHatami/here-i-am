package com.tuga.konum.providers

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.tuga.konum.domain.sources.LocationSource
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LocationProvider @Inject constructor(
  private val fusedLocationProviderClient: FusedLocationProviderClient
) :
  LocationSource {

  private var lastLocation: PublishSubject<Location> = PublishSubject.create()

  override fun getCurrentLocation(): PublishSubject<Location> {

    try {
      fusedLocationProviderClient.lastLocation
        .addOnSuccessListener {
          it?.let { location -> lastLocation.onNext(location) }
        }
    } catch (exception: SecurityException) {

    }

    return lastLocation
  }

}
