package com.tuga.konum.di

import com.tuga.konum.view.ui.signup.PhoneNumberFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
  @ContributesAndroidInjector
  abstract fun contributePhoneNumberFragment(): PhoneNumberFragment
}
