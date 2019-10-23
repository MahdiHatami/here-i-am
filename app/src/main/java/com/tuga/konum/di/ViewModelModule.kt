package com.tuga.konum.di

import com.tuga.konum.view.ui.main.MainViewModel
import com.tuga.konum.view.ui.signup.SignupActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  factory { MainViewModel(get()) }
  factory { SignupActivityViewModel(get()) }
}
