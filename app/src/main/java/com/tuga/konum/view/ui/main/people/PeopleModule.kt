package com.tuga.konum.view.ui.main.people

import androidx.lifecycle.ViewModel
import com.tuga.konum.di.ViewModelBuilder
import com.tuga.konum.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class PeopleModule {

  @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
  internal abstract fun peopleFragment(): PeopleFragment

  @Binds
  @IntoMap
  @ViewModelKey(PeopleViewModel::class)
  abstract fun bindPeopleViewModel(viewModel: PeopleViewModel): ViewModel
}
