package com.tuga.konum.di

import androidx.lifecycle.ViewModel
import com.tuga.konum.view.ui.signup.password.PasswordFragment
import com.tuga.konum.view.ui.signup.password.PasswordViewModel
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
abstract class PasswordModule {

  @ContributesAndroidInjector(
    modules = [
      ViewModelBuilder::class
    ]
  )

  internal abstract fun passwordFragment(): PasswordFragment

  @Binds
  @IntoMap
  @ViewModelKey(PasswordViewModel::class)
  abstract fun bindViewModel(viewModel: PasswordViewModel): ViewModel
}
