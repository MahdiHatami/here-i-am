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
  var userRepository: UserRepositoryImpl? = null
    @VisibleForTesting set

  fun provideUserRepository(context: Context): UserRepositoryImpl {
    synchronized(this) {
      return userRepository ?: userRepository ?: createUserRepository(context)
    }
  }

  private fun createUserRepository(context: Context): UserRepositoryImpl {
    database = Room.databaseBuilder(
      context.applicationContext,
      KonumDatabase::class.java, "konum.db"
    ).build()

    return UserRepositoryImpl(
      UserLocalDataSource(database!!.userDao()),
      UserRemoteDataSource(database!!.userDao())
    )
  }

}
