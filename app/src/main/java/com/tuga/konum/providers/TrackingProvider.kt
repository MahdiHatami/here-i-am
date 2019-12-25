package com.tuga.konum.providers

import android.content.Context
import android.content.Intent
import android.location.Location
import com.tuga.konum.domain.service.TrackingService
import com.tuga.konum.domain.sources.TrackingSource
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class TrackingProvider @Inject constructor(
  val application: Context, val service: TrackingService
) :
  TrackingSource {

  private val intent = Intent(application, service::class.java)

  override fun stopTracking() {
    application.stopService(intent)
  }

  override fun startTracking(): PublishSubject<Location> {
    application.startService(intent)
    return service.companion.lastLocation
  }

}