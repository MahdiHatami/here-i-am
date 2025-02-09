package com.tuga.konum.data.source.local

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.models.network.CreateApplicantDto
import com.tuga.konum.domain.models.network.CreateUserDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Concrete implementation of a data source as a db.
 */
class UserLocalDataSource @Inject constructor(
  private val userDao: UserDao,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserDataSource {

  override suspend fun deleteUser(phoneNumber: String) {
    userDao.deleteUser(phoneNumber)
  }

  override suspend fun getVerificationCode(dto: CreateApplicantDto): ApiResponse<Boolean> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun checkVerificationCode(dto: CheckVerificationCodeDto): ApiResponse<Boolean> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun createUser(dto: CreateUserDto): ApiResponse<Boolean> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
