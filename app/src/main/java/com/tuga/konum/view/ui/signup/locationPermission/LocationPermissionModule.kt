package com.tuga.konum.view.ui.signup.locationPermission

import androidx.lifecycle.ViewModel
import com.tuga.konum.di.ViewModelBuilder
import com.tuga.konum.di.ViewModelKey
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
