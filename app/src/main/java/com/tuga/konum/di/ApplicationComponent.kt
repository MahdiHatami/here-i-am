package com.tuga.konum.di

import android.app.Application
import com.tuga.konum.KonumApplication
import com.tuga.konum.data.source.local.DatabaseModule
import com.tuga.konum.di.module.ContextModule
import com.tuga.konum.view.ui.main.people.PeopleModule
import com.tuga.konum.view.ui.signup.circleOperation.CreateCircleModule
import com.tuga.konum.view.ui.signup.email.EmailModule
import com.tuga.konum.view.ui.signup.locationPermission.LocationPermissionModule
import com.tuga.konum.view.ui.signup.password.PasswordModule
import com.tuga.konum.view.ui.signup.phone.PhoneNumberModule
import com.tuga.konum.view.ui.signup.profile.ProfileModule
import com.tuga.konum.view.ui.signup.smsVerfication.SmsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
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
    AndroidInjectionModule::class,
    ApplicationModule::class,
    BaseModule::class,
    ContextModule::class,
    DataModule::class,
    DatabaseModule::class,
    DomainModule::class,

    // SignUp fragments
    PhoneNumberModule::class,
    SmsModule::class,
    PasswordModule::class,
    EmailModule::class,
    ProfileModule::class,
    LocationPermissionModule::class,
    CreateCircleModule::class,
    // Main fragments
    PeopleModule::class
  ]
)
interface ApplicationComponent : AndroidInjector<KonumApplication> {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance application: Application): ApplicationComponent
  }
}
