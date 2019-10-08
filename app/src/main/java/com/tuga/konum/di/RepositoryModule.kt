package com.tuga.konum.di

import com.tuga.konum.repository.UserRepository
import org.koin.dsl.module


val repositoryModule = module {
    single(createdAtStart = false) { UserRepository() }
}
