package com.tuga.konum.data.source

import com.tuga.konum.data.Result
import com.tuga.konum.models.entity.User

interface UserRepository {

  suspend fun getUser(): Result<User>
  suspend fun saveUser(user: User)
  suspend fun deleteUsers();
}
