package com.tuga.konum.di

import com.tuga.konum.domain.repository.TrackRepository
import com.tuga.konum.domain.repository.UserRepository
import com.tuga.konum.domain.service.TrackingService
import com.tuga.konum.domain.usecase.tracking.TrackingInteractor
import com.tuga.konum.domain.usecase.tracking.TrackingUseCases
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

const val SCHEDULER_MAIN_THREAD = "mainThread"
const val SCHEDULER_IO = "io"

@Module
object ApplicationModule {

  @Singleton
  @Provides
  fun provideIoDispatcher() = Dispatchers.IO

  @Provides
  @Named(SCHEDULER_MAIN_THREAD)
  fun provideAndroidMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

  @Provides
  @Named(SCHEDULER_IO)
  fun provideIoScheduler(): Scheduler = Schedulers.io()

  @Singleton
  @Provides
  fun provideTrackingService(): TrackingService = TrackingService()

  @Provides
  fun provideTrackingUseCases(repository: TrackRepository): TrackingUseCases =
    TrackingInteractor(repository)
}
