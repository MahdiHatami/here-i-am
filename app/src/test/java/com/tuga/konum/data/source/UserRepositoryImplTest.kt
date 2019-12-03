package com.tuga.konum.data.source

import com.google.common.truth.Truth.assertThat
import com.tuga.konum.CoroutinesTestRule
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.data.source.local.UserDao
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.KonumService
import com.tuga.konum.data.source.remote.UserRemoteDataSource
import com.tuga.konum.models.entity.User
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

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