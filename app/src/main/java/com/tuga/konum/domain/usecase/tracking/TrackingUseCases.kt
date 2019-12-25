package com.tuga.konum.domain.usecase.tracking

import com.google.android.gms.maps.model.LatLng
import com.tuga.konum.domain.models.entity.Track
import io.reactivex.Observable

interface TrackingUseCases {

  fun isTracking(): Boolean

  fun startTracking(): Observable<LatLng>?

  fun stopTracking()

  fun syncTrack()

  fun getCurrentLocation(): Observable<LatLng>?

  fun getLastLocation(): LatLng?

  fun getAllTracks(): List<Track?>

  fun getTrackDetails(): List<LatLng>

}