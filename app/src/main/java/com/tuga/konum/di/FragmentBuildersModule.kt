package com.tuga.konum.di

import com.tuga.konum.view.ui.signup.EmailFragment
import com.tuga.konum.view.ui.signup.PasswordFragment
import com.tuga.konum.view.ui.signup.PhoneNumberFragment
import com.tuga.konum.view.ui.signup.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
  @ContributesAndroidInjector
  abstract fun contributePhoneNumberFragment(): PhoneNumberFragment

  @ContributesAndroidInjector
  abstract fun contributePasswordFragment(): PasswordFragment

  @ContributesAndroidInjector
  abstract fun contributeEmailFragment(): EmailFragment

  @ContributesAndroidInjector
  abstract fun contributeProfileFragment(): ProfileFragment
}
