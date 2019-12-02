package com.tuga.konum

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

abstract class BaseApplication: DaggerApplication(), HasAndroidInjector {
  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    Stetho.initializeWithDefaults(this)
  }

  abstract override fun applicationInjector(): AndroidInjector<out DaggerApplication>

  override fun androidInjector(): AndroidInjector<Any> {
    return dispatchingAndroidInjector
  }
}