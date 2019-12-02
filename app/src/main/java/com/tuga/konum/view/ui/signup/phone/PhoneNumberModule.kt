package com.tuga.konum.view.ui.signup.phone

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
abstract class PhoneNumberModule {

  @ContributesAndroidInjector(
    modules = [
      ViewModelBuilder::class
    ]
  )

  internal abstract fun phoneNumberFragment(): PhoneNumberFragment

  @Binds
  @IntoMap
  @ViewModelKey(PhoneNumberViewModel::class)
  abstract fun bindViewModel(viewModel: PhoneNumberViewModel): ViewModel
}
