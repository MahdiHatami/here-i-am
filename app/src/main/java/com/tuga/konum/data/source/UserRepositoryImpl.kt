package com.tuga.konum.data.source

import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.UserRemoteDataSource
import com.tuga.konum.models.entity.User
import com.tuga.konum.util.EspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Concrete impl to load user from the data sources into a cache
 */
class UserRepositoryImpl(
  private val userLocalDataSource: UserLocalDataSource,
  private val userRemoteDataSource: UserRemoteDataSource,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

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
