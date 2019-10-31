package com.tuga.konum

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import com.tuga.konum.di.DaggerApplicationComponent
import timber.log.Timber

/**
 * An [Application] that uses Dagger for Dependency Injection.
 *
 * Also, sets up Timber in the DEBUG BuildConfig. Read Timber's documentation for production setups.
 */
open class KonumApplication : DaggerApplication() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerApplicationComponent.factory().create(applicationContext)
  }

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    Stetho.initializeWithDefaults(this)
  }
}
