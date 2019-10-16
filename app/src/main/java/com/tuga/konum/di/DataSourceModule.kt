package com.tuga.konum.di

import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.UserRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
  single(createdAtStart = false) { UserLocalDataSource(get()) }
  single(createdAtStart = false) { UserRemoteDataSource(get()) }
}
