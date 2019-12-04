package com.tuga.konum.di

import com.tuga.konum.util.DefaultErrorFactory
import com.tuga.konum.util.ErrorFactory
import dagger.Binds
import dagger.Module

@Module
abstract class BaseModule {

  @Binds
  internal abstract fun provideErrorFactory(defaultErrorFactory: DefaultErrorFactory): ErrorFactory
}
