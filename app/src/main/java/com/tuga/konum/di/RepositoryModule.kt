package com.tuga.konum.di

import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.data.source.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
  single<UserRepository> { UserRepositoryImpl(get(), get()) }
}
