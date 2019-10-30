package com.tuga.konum.util

import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.models.entity.User
import kotlinx.coroutines.runBlocking

fun UserRepository.saveUserBlocking(user: User) = runBlocking {
  this@saveUserBlocking.saveUser(user)
}

fun UserRepository.getUserBlocking() = runBlocking {
  this@getUserBlocking.getUser()
}
