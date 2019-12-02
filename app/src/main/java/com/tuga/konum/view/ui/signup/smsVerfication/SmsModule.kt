package com.tuga.konum.view.ui.signup.smsVerfication

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
abstract class SmsModule {

  @ContributesAndroidInjector(
    modules = [
      ViewModelBuilder::class
    ]
  )

  internal abstract fun smsFragment(): SmsFragment

  @Binds
  @IntoMap
  @ViewModelKey(SmsViewModel::class)
  abstract fun bindViewModel(viewModel: SmsViewModel): ViewModel
}
