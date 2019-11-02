package com.tuga.konum.di

import androidx.lifecycle.ViewModel
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
abstract class ProfileModule {

  @ContributesAndroidInjector(
    modules = [
      ViewModelBuilder::class
    ]
  )

  internal abstract fun profileFragment(): ProfileFragment

  @Binds
  @IntoMap
  @ViewModelKey(ProfileViewModel::class)
  abstract fun bindViewModel(viewModel: ProfileViewModel): ViewModel
}
