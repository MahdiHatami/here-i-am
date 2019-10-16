package com.tuga.konum.data.source.remote

import com.tuga.konum.data.Result
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.data.source.local.UserDao
import com.tuga.konum.models.entity.User

class UserRemoteDataSource internal constructor(
  userDao: UserDao
) : UserDataSource {

  override suspend fun getUser(): Result<User> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun saveUser(user: User) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
