package com.tuga.konum.domain.repository

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.UserRemoteDataSource
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.models.network.CreateApplicantDto
import com.tuga.konum.domain.models.network.CreateCircleDto
import com.tuga.konum.domain.models.network.CreateUserDto
import com.tuga.konum.util.EspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UserRepository {
  suspend fun getUser(): Result<User>
  suspend fun saveUser(user: User)
  suspend fun deleteUser(phoneNumber: String)
  suspend fun getVerificationCode(dto: CreateApplicantDto): ApiResponse<Boolean>
  suspend fun checkVerificationCode(dto: CheckVerificationCodeDto): ApiResponse<Boolean>
  suspend fun createUser(dto: CreateUserDto): ApiResponse<Boolean>
}

/**
 * Concrete implementation to load users from the data sources into a cache.
 *
 * To simplify the sample, this repository only uses the local data source only if the remote
 * data source fails. Remote is the source of truth.
 */
class UserRepositoryImpl @Inject constructor(
  private val userLocalDataSource: UserLocalDataSource,
  private val userRemoteDataSource: UserRemoteDataSource,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

  override suspend fun getVerificationCode(dto: CreateApplicantDto): ApiResponse<Boolean> {
    return userRemoteDataSource.getVerificationCode(dto)
  }

  override suspend fun checkVerificationCode(dto: CheckVerificationCodeDto): ApiResponse<Boolean> {
    return userRemoteDataSource.checkVerificationCode(dto)
  }

  override suspend fun createUser(dto: CreateUserDto): ApiResponse<Boolean> {
    return userRemoteDataSource.createUser(dto)
  }

  override suspend fun deleteUser(phoneNumber: String) {
    coroutineScope {
      launch { userLocalDataSource.deleteUser(phoneNumber) }
    }
  }

  override suspend fun saveUser(user: User) {
    coroutineScope {
      launch { userLocalDataSource.saveUser(user) }
    }
  }

  override suspend fun getUser(): Result<User> {
    EspressoIdlingResource.increment() // Set app as busy.

    return withContext(ioDispatcher) {
      // remote first if fails read from local
      EspressoIdlingResource.decrement() // set app as idle.
      return@withContext getUserFromLocal()
    }
  }

  private suspend fun getUserFromLocal(): Result<User> {
    val localUser = userLocalDataSource.getUser()
    if (localUser is Success) return localUser
    return Error(Exception("Error fetching from remote and local"))
  }

}
