package com.tuga.konum.domain.repository

import android.location.Location
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.maps.model.LatLng
import com.tuga.konum.data.PrefStorage
import com.tuga.konum.data.source.local.TrackingDao
import com.tuga.konum.di.SCHEDULER_IO
import com.tuga.konum.domain.models.entity.Track
import com.tuga.konum.domain.models.entity.TrackDetail
import com.tuga.konum.domain.sources.LocationSource
import com.tuga.konum.domain.sources.TrackingSource
import com.tuga.konum.domain.worker.SyncWorker
import com.tuga.konum.util.StringUtils.makeMapUrl
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.DateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

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
  private val trackingDao: TrackingDao,
  private val locationSource: LocationSource,
  private val trackingSource: TrackingSource,
  private val prefStorage: PrefStorage,
  @Named(SCHEDULER_IO) val subscribeOnScheduler: Scheduler
) : TrackRepository {

  override var trackId: Long = 0
  private lateinit var trackingData: Observable<Location>
  private val disposable = CompositeDisposable()

  override fun startTracking(): Observable<LatLng>? {
    // Set tracking is on
    prefStorage.writeIsTracking(true)
    // Add a tracking to the DB
    trackId =
      trackingDao.insertTrack(
        Track(
          date = DateFormat.getDateTimeInstance().format(Date())
        )
      )
    // Get the observable and start the tracking
    trackingData = trackingSource.startTracking()
    // Start listening to location changes and insert to db on receive
    disposable.add(trackingData.subscribeOn(subscribeOnScheduler).subscribe {
      trackingDao.insertTrackDetail(
        TrackDetail(
          trackId = trackId.toInt(),
          y = it.longitude.toString(),
          x = it.latitude.toString()
        )
      )
    })
    // Map to LatLng and return the observable
    return trackingData.subscribeOn(subscribeOnScheduler)
      .map { t: Location -> LatLng(t.latitude, t.longitude) }
  }

  override fun stopTracking() {
    // Set tracking is off
    prefStorage.writeIsTracking(false)
    // Stop the tracking
    trackingSource.stopTracking()
    // User stopped tracking without moving
    if (trackingDao.getTrackDetails(trackId.toInt()).size == 1)
      trackingDao.deleteTrack(trackId)
    // Clear the disposable
    disposable.clear()
  }

  override fun syncTrack() {
    val constraints =
      Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    val request: OneTimeWorkRequest =
      OneTimeWorkRequest.Builder(SyncWorker::class.java)
        .setConstraints(constraints)
        .addTag("sync-track")
        .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS)
        .build()

    WorkManager.getInstance()
      .beginUniqueWork("sync-track", ExistingWorkPolicy.KEEP, request)
      .enqueue()
  }

  override fun getCurrentLocation(): Observable<LatLng> =
    locationSource.getCurrentLocation().subscribeOn(Schedulers.io()).map { location ->
      LatLng(
        location.latitude,
        location.longitude
      )
    }.doOnNext { lastLocation -> prefStorage.writeLastLocation(lastLocation) }

  override fun getLastLocation(): LatLng? = prefStorage.readLastLocation()

  override fun getTrackingStatus(): Boolean = prefStorage.readIsTracking()

  override fun getTrackingDetail() = trackingDao.getTrackDetails(trackId = trackId.toInt()).map {
    LatLng(
      it.x!!.toDouble(),
      it.y!!.toDouble()
    )
  }

  override fun getAllTracks(): List<Track> {
    return trackingDao.getAllTracks().map { track ->
      track.tid.let { trackId ->
        trackingDao.getTrackDetails(trackId).map { trackDetail ->
          LatLng(
            trackDetail.x!!.toDouble(),
            trackDetail.y!!.toDouble()
          )
        }
      }.let { points ->
        makeMapUrl(points)
      }.let { mapUrl ->
        Track(
          track.tid, mapUrl, track.date.toString()
        )
      }
    }
  }
}