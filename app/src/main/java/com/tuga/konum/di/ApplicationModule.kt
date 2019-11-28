package com.tuga.konum.di

import android.content.Context
import androidx.room.Room
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.data.source.UserRepositoryImpl
import com.tuga.konum.data.source.local.KonumDatabase
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.UserRemoteDataSource
import com.tuga.konum.data.source.remote.UserService
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
    userService: UserService
  ): UserDataSource {
    return UserRemoteDataSource(
      userService
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
