package com.tuga.konum.domain.repository

import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.tuga.konum.domain.models.entity.Track
import com.tuga.konum.domain.sources.TrackingSource
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface TrackRepository {
  var trackId: Long
  fun startTracking(): Observable<LatLng>?
  fun stopTracking()
  fun syncTrack()
  fun getCurrentLocation(): Observable<LatLng>?
  fun getLastLocation(): LatLng?
  fun getTrackingStatus(): Boolean
  fun getTrackingDetail(): List<LatLng>
  fun getAllTracks(): List<Track?>
}

class DefaultTrackRepository @Inject constructor(
  private val locationSource: LocationSource,
  private val trackingSource: TrackingSource,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TrackRepository {
  override var trackId: Long = 0

  override fun startTracking(): Observable<LatLng>? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun stopTracking() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun syncTrack() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getCurrentLocation(): Observable<LatLng>? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getLastLocation(): LatLng? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTrackingStatus(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTrackingDetail(): List<LatLng> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getAllTracks(): List<Track?> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}