package com.tuga.konum.di

import com.tuga.konum.domain.service.TrackingService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
object ApplicationModule {

  @Singleton
  @Provides
  fun provideIoDispatcher() = Dispatchers.IO

  @Singleton
  @Provides
  fun provideTrackingService(): TrackingService = TrackingService()
}
