package com.tuga.konum

import com.tuga.konum.base.BaseApplication
import com.tuga.konum.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * An [Application] that uses Dagger for Dependency Injection.
 *
 * Also, sets up Timber in the DEBUG BuildConfig. Read Timber's documentation for production setups.
 */
class KonumApplication : BaseApplication() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerApplicationComponent.factory().create(this).also { appComponent ->
      appComponent.inject(this@KonumApplication)
    }
  }
}
