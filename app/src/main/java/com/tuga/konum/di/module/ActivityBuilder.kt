package com.tuga.konum.di.module

import com.tuga.konum.di.scope.ActivityScope
import com.tuga.konum.view.ui.signup.SignupActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

//    @ActivityScope
//    @ContributesAndroidInjector(modules = [MainActivityModule::class])
//    internal abstract fun contributeMainActivity(): SignupActivity
}
