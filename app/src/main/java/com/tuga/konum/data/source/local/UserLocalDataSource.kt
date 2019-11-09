package com.tuga.konum.data.source.local

import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.models.entity.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db.
 */
class UserLocalDataSource internal constructor(
  private val userDao: UserDao,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserDataSource {

  override suspend fun deleteUser(phoneNumber: String) {
    userDao.deleteUser(phoneNumber)
  }

  override suspend fun getUser(): Result<User> = withContext(ioDispatcher) {
    return@withContext try {
      Success(userDao.getUser())
    } catch (e: Exception) {
      Error(e)
    }
  }

  override suspend fun saveUser(user: User) = withContext(ioDispatcher) {
    userDao.deleteUsers()
    userDao.insertUser(user)
  }
}
