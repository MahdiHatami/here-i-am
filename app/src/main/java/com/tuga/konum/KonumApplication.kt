package com.tuga.konum

import android.annotation.SuppressLint
import android.app.Application
import com.facebook.stetho.Stetho
import com.tuga.konum.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


@SuppressLint("Registered")
class KonumApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KonumApplication)
            modules(viewModelModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Stetho.initializeWithDefaults(this)
    }
}
