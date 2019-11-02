package com.tuga.konum.di

import androidx.lifecycle.ViewModel
import com.tuga.konum.view.ui.signup.locationPermission.LocationPermissionFragment
import com.tuga.konum.view.ui.signup.locationPermission.LocationPermissionFragment_MembersInjector
import com.tuga.konum.view.ui.signup.locationPermission.LocationPermissionViewModel
import com.tuga.konum.view.ui.signup.profile.ProfileFragment
import com.tuga.konum.view.ui.signup.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Dagger module for the phone number feature.
 */
@Module
abstract class LocationPermissionModule {

  @ContributesAndroidInjector(
    modules = [
      ViewModelBuilder::class
    ]
  )

  internal abstract fun locationPermissionFragment(): LocationPermissionFragment

  @Binds
  @IntoMap
  @ViewModelKey(LocationPermissionViewModel::class)
  abstract fun bindViewModel(viewModel: LocationPermissionViewModel): ViewModel
}
