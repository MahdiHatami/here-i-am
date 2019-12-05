package com.tuga.konum.data.source

import com.tuga.konum.CoroutinesTestRule
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.UserRemoteDataSource
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

/**
 * Unit tests for the implementation of the in-memory repository with cache
 */

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {
  private val newUser =
    User("5070933798", "mahdi", "123456", "iz.hatami@gmail.com")

  @get:Rule
  val coroutinesTestRule = CoroutinesTestRule()

  private lateinit var localDataSource: UserLocalDataSource
  private lateinit var remoteDataSource: UserRemoteDataSource
  private lateinit var repository: UserRepository

}