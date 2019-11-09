package com.tuga.konum.data.source

import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.models.entity.User
import java.lang.Exception

class FakeDataSource(var dbuser: User? = null) : UserDataSource {
  override suspend fun getUser(): Result<User> {
    dbuser?.let { return Success(it) }
    return Error(
      Exception("User not found")
    )
  }

  override suspend fun saveUser(user: User) {
    dbuser = user
  }

  override suspend fun deleteUser(phoneNumber: String) {
    dbuser = null
  }
}