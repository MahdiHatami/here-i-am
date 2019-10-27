package com.tuga.konum

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.tuga.konum.data.source.UserRepositoryImpl
import com.tuga.konum.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class KonumApplication : Application(), HasActivityInjector {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  // Depends on the flavor,
  val userRepository: UserRepositoryImpl
    get() = ServiceLocator.provideUserRepository(this)

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    AppInjector.init(this)

    Stetho.initializeWithDefaults(this)
  }

  override fun activityInjector() = dispatchingAndroidInjector

}
