package com.tuga.konum.di

import com.tuga.konum.domain.repository.UserRepository
import com.tuga.konum.domain.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DomainModule {

  @Singleton
  @Binds
  abstract fun bindRepository(repo: UserRepositoryImpl): UserRepository
}
