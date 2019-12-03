package com.tuga.konum.di

import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.data.source.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DomainModule {

  @Singleton
  @Binds
  abstract fun bindRepository(repo: UserRepositoryImpl): UserRepository
}
