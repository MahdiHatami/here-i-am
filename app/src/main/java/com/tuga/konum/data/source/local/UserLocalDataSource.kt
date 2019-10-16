package com.tuga.konum.data.source.local

import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Success
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.models.entity.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.tuga.konum.data.Result.Error
import java.lang.Exception

class UserLocalDataSource internal constructor(
  private val userDao: UserDao
) : UserDataSource {
  override suspend fun deleteUser(phoneNumber: String) {
    userDao.deleteUser(phoneNumber)
  }

  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

  override suspend fun getUser(): Result<User> = withContext(ioDispatcher) {
    return@withContext try {
      Success(userDao.getUser())
    } catch (e: Exception) {
      Error(e)
    }
  }

  override suspend fun saveUser(user: User) = withContext(ioDispatcher) {
    userDao.insertUser(user)
  }
}
