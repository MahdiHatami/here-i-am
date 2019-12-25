package com.tuga.konum.domain.usecase.tracking

import com.tuga.konum.domain.repository.TrackRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackingInteractor @Inject constructor(
  private val repository: TrackRepository
) :
  TrackingUseCases {

  override fun getLastLocation() = repository.getLastLocation()

  override fun isTracking() = repository.getTrackingStatus()

  override fun stopTracking() = repository.stopTracking()

  override fun syncTrack() = repository.syncTrack()

  override fun getCurrentLocation() = repository.getCurrentLocation()

  override fun startTracking() = repository.startTracking()

  override fun getTrackDetails() = repository.getTrackingDetail()

  override fun getAllTracks() = repository.getAllTracks()

}