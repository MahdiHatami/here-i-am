package com.tuga.konum.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tuga.konum.models.entity.User

@Dao
interface UserDao {

  @Query("SELECT * FROM User LIMIT 1")
  suspend fun getUser(): User

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUser(user: User)

  @Query("DELETE FROM user WHERE phoneNumber = :phoneNumber")
  suspend fun deleteUser(phoneNumber: String)
}
