package com.tuga.konum.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.models.entity.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {
  private lateinit var database: KonumDatabase

  // Set the main coroutines dispatcher for unit testing.
  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  // Executes each task synchronously using Architecture Components.
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun initDb() {
    // using an in-memory database because the information stored here disappears when the
    // process is killed
    database = Room.inMemoryDatabaseBuilder(
      ApplicationProvider.getApplicationContext(),
      KonumDatabase::class.java
    ).allowMainThreadQueries().build()
  }

  @After
  fun closeDb() = database.close()

  @Test
  fun insertUserAndGetUser() = runBlockingTest {
    // GIVEN - insert user
    val user = User("99999999", "mahdi")
    database.userDao().insertUser(user)

    // WHEN - get user from database
    var loaded = database.userDao().getUser()

    // THEN - the loaded data contains the expected value
    assertThat<User>(loaded as User, notNullValue())
    assertThat(loaded.phoneNumber, `is`(user.phoneNumber))
    assertThat(loaded.username, `is`(user.username))
    assertThat(loaded.email, `is`(user.email))
  }
}
