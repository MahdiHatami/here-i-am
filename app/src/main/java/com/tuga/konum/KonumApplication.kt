package com.tuga.konum

import android.annotation.SuppressLint
import android.app.Application
import com.facebook.stetho.Stetho
import com.tuga.konum.di.dataSourceModule
import com.tuga.konum.di.persistenceModule
import com.tuga.konum.di.repositoryModule
import com.tuga.konum.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class KonumApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger()
      androidContext(this@KonumApplication)
      modules(viewModelModule)
      modules(repositoryModule)
      modules(persistenceModule)
      modules(dataSourceModule)
    }

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    Stetho.initializeWithDefaults(this)
  }
}
