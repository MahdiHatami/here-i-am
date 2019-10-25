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
package com.tuga.konum

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.data.source.UserRepositoryImpl
import com.tuga.konum.data.source.local.KonumDatabase
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.UserRemoteDataSource

/**
 * A Service Locator for the [UserRepository]. This is the mock version, with a
 * [FakeTasksRemoteDataSource].
 */
object ServiceLocator {

  private val lock = Any()
  private var database: KonumDatabase? = null
  @Volatile
  var userRepository: UserRepository? = null
    @VisibleForTesting set

  fun provideUserRepository(context: Context): UserRepository {
    synchronized(this) {
      return userRepository ?: userRepository ?: createUserRepository(context)
    }
  }

  private fun createUserRepository(context: Context): UserRepository {
    database = Room.databaseBuilder(
      context.applicationContext,
      KonumDatabase::class.java, "konum.db"
    ).build()

    return UserRepositoryImpl(
      UserLocalDataSource(database!!.userDao()),
      UserRemoteDataSource
    )
  }

}
