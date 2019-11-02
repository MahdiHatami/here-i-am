package com.tuga.konum.di

import androidx.lifecycle.ViewModel
import com.tuga.konum.view.ui.signup.email.EmailFragment
import com.tuga.konum.view.ui.signup.email.EmailViewModel
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
abstract class EmailModule {

  @ContributesAndroidInjector(
    modules = [
      ViewModelBuilder::class
    ]
  )

  internal abstract fun emailFragment(): EmailFragment

  @Binds
  @IntoMap
  @ViewModelKey(EmailViewModel::class)
  abstract fun bindViewModel(viewModel: EmailViewModel): ViewModel
}
