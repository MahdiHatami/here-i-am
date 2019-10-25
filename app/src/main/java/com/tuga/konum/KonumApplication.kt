package com.tuga.konum

import android.app.Application
import com.facebook.stetho.Stetho
import com.tuga.konum.data.source.UserRepository
import timber.log.Timber

class KonumApplication : Application() {
  // Depends on the flavor,
  val userRepository: UserRepository
    get() = ServiceLocator.provideUserRepository(this)

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    Stetho.initializeWithDefaults(this)
  }
}
