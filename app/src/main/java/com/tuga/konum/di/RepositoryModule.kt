package com.tuga.konum.di

import com.tuga.konum.data.source.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
  single(createdAtStart = false) { UserRepositoryImpl(get(), get(), get()) }
}
