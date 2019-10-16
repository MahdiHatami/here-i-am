package com.tuga.konum.data.source

import com.tuga.konum.models.entity.User
import com.tuga.konum.data.Result

/**
 * Main Entry point for user data
 */
interface UserDataSource {

  suspend fun getUser(): Result<User>
  suspend fun saveUser(user: User)
}
