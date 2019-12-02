package com.tuga.konum.view.ui.signup.email

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
