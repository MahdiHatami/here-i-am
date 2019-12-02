package com.tuga.konum.di

import android.content.Context
import com.tuga.konum.KonumApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Main component for the application.
 *
 * See the `TestApplicationComponent` used in UI tests.
 */
@Singleton
@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    DataModule::class,
    PhoneNumberModule::class,
    SmsModule::class,
    PasswordModule::class,
    EmailModule::class,
    ProfileModule::class,
    LocationPermissionModule::class
  ]
)
interface ApplicationComponent : AndroidInjector<KonumApplication> {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }
}
