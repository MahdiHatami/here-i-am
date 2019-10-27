package com.tuga.konum.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuga.konum.view.ui.signup.SignupActivityViewModel
import com.tuga.konum.viewmodel.KonumViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(SignupActivityViewModel::class)
  abstract fun bindSignupActivityViewModel(signupActivityViewModel: SignupActivityViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: KonumViewModelFactory) : ViewModelProvider.Factory
}
