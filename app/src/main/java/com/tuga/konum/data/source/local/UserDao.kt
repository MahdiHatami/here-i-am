package com.tuga.konum.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tuga.konum.domain.models.entity.User

@Dao
abstract class UserDao {

  @Query("SELECT * FROM User LIMIT 1")
  abstract suspend fun getUser(): User

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun insertUser(user: User)

  @Query("DELETE FROM USER WHERE phone_umber = :phoneNumber")
  abstract suspend fun deleteUser(phoneNumber: String)

  @Query("DELETE FROM USER")
  abstract suspend fun deleteUsers()
}
