package com.tuga.konum.data.source

import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.UserRemoteDataSource
import com.tuga.konum.di.ApplicationModule
import com.tuga.konum.models.entity.User
import com.tuga.konum.util.EspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Concrete implementation to load users from the data sources into a cache.
 *
 * To simplify the sample, this repository only uses the local data source only if the remote
 * data source fails. Remote is the source of truth.
 */
class UserRepositoryImpl @Inject constructor(
  @ApplicationModule.UserLocalDataSource private val userLocalDataSource: UserLocalDataSource,
  @ApplicationModule.UserRemoteDataSource private val userRemoteDataSource: UserRemoteDataSource,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

  override suspend fun deleteUsers() {
    coroutineScope {
      launch { userLocalDataSource.deleteUsers() }
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
