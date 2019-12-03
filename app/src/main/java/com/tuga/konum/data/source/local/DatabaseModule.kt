package com.tuga.konum.data.source.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
  companion object {
    const val DB_NAME = "konum.db"
  }

  @Provides
  @Singleton
  fun provideDatabase(context: Context) = Room
    .databaseBuilder(context, KonumDatabase::class.java, DB_NAME)
    .addMigrations()
    .build()

  @Provides
  @Singleton
  fun provideUserDao(db: KonumDatabase) = db.userDao()

}