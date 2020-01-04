package com.tuga.konum.domain.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tuga.konum.data.source.remote.KonumService
import com.tuga.konum.domain.models.network.TrackDto
import com.tuga.konum.domain.repository.DefaultTrackRepository
import javax.inject.Inject

class SyncWorker(appContext: Context, workerParams: WorkerParameters) :
  Worker(appContext, workerParams) {

  @Inject
  lateinit var konumService: KonumService

  @Inject
  lateinit var trackRepository: DefaultTrackRepository

  override fun doWork(): Result {
    try {

      val response = konumService
        .syncTrackToServer(
          TrackDto(
            trackRepository.trackId.toString(),
            trackRepository.getTrackingDetail()
          )
        )
        .execute()

      if (response.isSuccessful) {
        return Result.success()
      } else {
        if (response.code() in (500..599)) {
          // try again if there is a server error
          return Result.retry()
        }
        return Result.failure()
      }

    } catch (e: Exception) {
      return Result.failure()
    }
  }
}