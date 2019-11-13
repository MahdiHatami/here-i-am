package com.tuga.konum.data.source

import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.models.entity.User
import java.util.LinkedHashMap

class FakeUserRepository : UserRepository {
  var userServiceData: LinkedHashMap<String, User> = LinkedHashMap()
  private var shouldReturnError = false

  fun setReturnError(value: Boolean) {
    shouldReturnError = value
  }

  override suspend fun getUser(): Result<User> {
    if (shouldReturnError) {
      return Error(Exception("Test Exception"))
    }
    userServiceData["5070933798"]?.let {
      return Success(it)
    }
    return Error(Exception("Could not find user"))
  }

  override suspend fun saveUser(user: User) {
    userServiceData[user.phoneNumber] = user
  }

  override suspend fun deleteUser(phoneNumber: String) {
    userServiceData.remove(phoneNumber)
  }

}
