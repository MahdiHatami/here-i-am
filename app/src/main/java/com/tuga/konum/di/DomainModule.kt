package com.tuga.konum.di

import com.tuga.konum.domain.repository.DefaultTrackRepository
import com.tuga.konum.domain.repository.TrackRepository
import com.tuga.konum.domain.repository.UserRepository
import com.tuga.konum.domain.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DomainModule {

  @Singleton
  @Binds
  abstract fun bindUserRepository(repo: UserRepositoryImpl): UserRepository

  @Singleton
  @Binds
  abstract fun bindTrackRepository(repo: DefaultTrackRepository): TrackRepository
}
