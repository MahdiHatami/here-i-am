package com.tuga.konum.data.source

import com.tuga.konum.data.Result
import com.tuga.konum.models.entity.User

class FakeUserRepository : UserRepository {
  override suspend fun getUser(): Result<User> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun saveUser(user: User) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun deleteUsers() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}
