package com.tuga.konum.di

import com.tuga.konum.view.ui.signup.SignupActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class SignupActivityModule {
  @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
  abstract fun contributeSignupActivity(): SignupActivity
}
