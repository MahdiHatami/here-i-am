package com.tuga.konum.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tuga.konum.domain.models.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class KonumDatabase : RoomDatabase() {
  abstract fun userDao(): UserDao
}
