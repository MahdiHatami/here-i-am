package com.tuga.konum.data.source

import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.models.entity.User
import java.lang.Exception

/**
 *
 */
class FakeUserRepository : UserRepository {
  override suspend fun deleteUsers() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  var userServiceData = User()
  private var shouldReturnError = false

  override suspend fun getUser(): Result<User> {
    if (shouldReturnError) {
      return Error(Exception("Test Exception"))
    }

    return Success(userServiceData)
  }

  override suspend fun saveUser(user: User) {
    userServiceData = user
  }
}
