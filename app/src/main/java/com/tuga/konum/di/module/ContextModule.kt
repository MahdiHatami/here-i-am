package com.tuga.konum.di.module

import android.app.Application
import android.content.Context

import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule {

  @Binds
  internal abstract fun bindsContext(application: Application): Context
}
