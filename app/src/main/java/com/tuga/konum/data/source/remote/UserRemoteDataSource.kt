package com.tuga.konum.data.source.remote

import com.tuga.konum.data.Result
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.data.source.local.UserDao
import com.tuga.konum.models.entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
  private val userDao: UserDao
) : UserDataSource {
  override suspend fun deleteUsers() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun deleteUser(phoneNumber: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun getUser(): Result<User> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun saveUser(user: User) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
