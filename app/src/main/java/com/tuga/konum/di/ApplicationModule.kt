/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tuga.konum.di

import android.content.Context
import androidx.room.Room
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.data.source.UserRepositoryImpl
import com.tuga.konum.data.source.local.KonumDatabase
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

  @Qualifier
  @Retention(RUNTIME)
  annotation class UserRemoteDataSource

  @Qualifier
  @Retention(RUNTIME)
  annotation class UserLocalDataSource

  @JvmStatic
  @Singleton
  @UserRemoteDataSource
  @Provides
  fun provideUserRemoteDataSource(
    database: KonumDatabase,
    ioDispatcher: CoroutineDispatcher
  ): UserDataSource {
    return UserRemoteDataSource(
      database.userDao(),
      ioDispatcher
    )
  }

  @JvmStatic
  @Singleton
  @Provides
  @UserLocalDataSource
  fun provideUserLocalDataSource(
    database: KonumDatabase,
    ioDispatcher: CoroutineDispatcher
  ): UserDataSource {
    return UserLocalDataSource(
      database.userDao(), ioDispatcher
    )
  }

  @JvmStatic
  @Singleton
  @Provides
  fun provideDataBase(context: Context): KonumDatabase {
    return Room.databaseBuilder(
      context.applicationContext,
      KonumDatabase::class.java,
      "Konum.db"
    ).build()
  }

  @JvmStatic
  @Singleton
  @Provides
  fun provideIoDispatcher() = Dispatchers.IO
}

@Module
abstract class ApplicationModuleBinds {

  @Singleton
  @Binds
  abstract fun bindRepository(repo: UserRepositoryImpl): UserRepository
}
