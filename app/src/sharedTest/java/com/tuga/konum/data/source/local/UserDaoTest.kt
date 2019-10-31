package com.tuga.konum.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
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
class UserDaoTest{

  private lateinit var db: KonumDatabase

  // Executes each task synchronously using Architecture Components.
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    // using an in-memory database for testing, since it doesn't survive killing the process
    db = Room.inMemoryDatabaseBuilder(
      ApplicationProvider.getApplicationContext(),
      KonumDatabase::class.java
    )
      .allowMainThreadQueries()
      .build()
  }

  @After
  fun cleanUp() {
    db.close()
  }

  @Test
  fun insertUserAndGetUser() = runBlockingTest {
    // GIVEN - insert user
    val user = User("99999999", "mahdi")
    db.userDao().insertUser(user)

    // WHEN - get user fromdb
    val loaded = db.userDao().getUser()

    // THEN - the loaded data contains the expected value
    assertThat<User>(loaded, notNullValue())
    assertThat(loaded.phoneNumber, `is`(user.phoneNumber))
    assertThat(loaded.username, `is`(user.username))
    assertThat(loaded.email, `is`(user.email))
  }
}
