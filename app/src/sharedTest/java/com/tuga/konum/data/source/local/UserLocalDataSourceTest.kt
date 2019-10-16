package com.tuga.konum.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.data.Result
import com.tuga.konum.data.Result.Success
import com.tuga.konum.data.succeeded
import com.tuga.konum.models.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration test for the [UserLocalDataSource]
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class UserLocalDataSourceTest {
  private lateinit var localDataSource: UserLocalDataSource
  private lateinit var database: KonumDatabase

  // Set the main coroutines dispatcher for unit testing.
  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  // Executes each task synchronously using Architecture Components.
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    // using an in-memory database for testing, since it doesn't survive killing the process
    database = Room.inMemoryDatabaseBuilder(
      ApplicationProvider.getApplicationContext(),
      KonumDatabase::class.java
    )
      .allowMainThreadQueries()
      .build()

//    figure it out how to do Dispatchers injection with koin di
//    localDataSource = TasksLocalDataSource(database.taskDao(), Dispatchers.Main)

    localDataSource = UserLocalDataSource(database.userDao())
  }

  @After
  fun cleanUp() {
    database.close()
  }

  @Test
  fun completeSignUp_retrieveUser() = runBlocking {
    // GIVEN - user
    val newUser = User("12345", "mahdi", isCompleted = true)
    localDataSource.saveUser(newUser)

    // WHEN - User retrieved
    val result = localDataSource.getUser()

    // THEN - same task is returned
    assertThat(result.succeeded, `is`(true))
    result as Success
    assertThat(result.data.phoneNumber, `is`("12345"))
    assertThat(result.data.username, `is`("mahdi"))
    assertThat(result.data.isCompleted, `is`(true))
  }

  @Test
  fun removeUser_userNotRetrievable() = runBlocking {
    // GIVEN - a user
    val newUser = User("12345")
    localDataSource.saveUser(newUser)

    // WHEN - user deleted
    localDataSource.deleteUser(newUser.phoneNumber)

    // THEN - user cannot be retrieval
    val result = localDataSource.getUser() as Result.Success
    assertThat(result.data, `is`(nullValue()))

  }

}
