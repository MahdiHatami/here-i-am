package com.tuga.konum.data.source.remote

import com.tuga.konum.data.Result
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.data.source.local.UserDao
import com.tuga.konum.models.entity.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

class UserRemoteDataSource internal constructor(
  private val userDao: UserDao,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserDataSource {
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
