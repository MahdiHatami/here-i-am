package com.tuga.konum.data.source

import com.google.common.truth.Truth.assertThat
import com.tuga.konum.data.Result.Error
import com.tuga.konum.data.Result.Success
import com.tuga.konum.models.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the implementation of the in-memory repository with cache
 */

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

  private val newUser =
    User("5070933798", "mahdi", "123456", "iz.hatami@gmail.com")

  private val localUser = newUser
  private val remoteUser = newUser

  private lateinit var userRemoteDataSource: FakeDataSource
  private lateinit var userLocalDataSource: FakeDataSource

  // class under test
  private lateinit var userRepository: UserRepositoryImpl

  @ExperimentalCoroutinesApi
  @Before
  fun createRepository() {
    userLocalDataSource = FakeDataSource(localUser)
    userRemoteDataSource = FakeDataSource(remoteUser)
    userRepository = UserRepositoryImpl(
      userRemoteDataSource, userLocalDataSource, Dispatchers.Unconfined
    )
  }

  @ExperimentalCoroutinesApi
  @Test
  fun getUser_emptyRepositoryAndUninitializedCache() = runBlockingTest {
    val emptySource = FakeDataSource()
    val tasksRepository = UserRepositoryImpl(
      emptySource, emptySource, Dispatchers.Unconfined
    )

    assertThat(tasksRepository.getUser() is Error).isTrue()
  }

  @ExperimentalCoroutinesApi
  @Test
  fun saveUser_saveToCacheLocalAndRemote() = runBlocking {
    // when
    userRepository.saveUser(newUser)

    // then
    assertThat(userLocalDataSource.dbuser).isEqualTo(newUser)

    val result = userRepository.getUser() as? Success
    assertThat(result?.data).isEqualTo(newUser)
  }

  @ExperimentalCoroutinesApi
  @Test
  fun deleteUser() = runBlockingTest {

    userRepository.saveUser(newUser)

    userRepository.deleteUser(newUser.phoneNumber)

    assertThat(userRemoteDataSource.getUser() is Error).isTrue()
  }
}