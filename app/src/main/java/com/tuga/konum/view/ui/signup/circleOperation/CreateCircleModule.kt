package com.tuga.konum.view.ui.signup.circleOperation

import androidx.lifecycle.ViewModel
import com.tuga.konum.di.ViewModelBuilder
import com.tuga.konum.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CreateCircleModule {

  @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
  internal abstract fun createCircleFragment(): CreateCircleFragment

  @Binds
  @IntoMap
  @ViewModelKey(CircleViewModel::class)
  abstract fun bindCircleViewModel(viewModel: CircleViewModel): ViewModel
}