package com.tuga.konum.view.ui.signup.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.data.source.FakeUserRepository
import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.models.entity.User
import com.tuga.konum.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.contracts.ExperimentalContracts

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProfileViewModelTest {

  // subject under test
  private lateinit var profileViewModel: ProfileViewModel

  // use fake repository to injected in the viewModel
  private lateinit var userRepository: FakeUserRepository

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  // for testing liveData
  @get:Rule
  var instaExecuRule = InstantTaskExecutorRule()

  private val user = User("5070933798", "mahdi", "123456")

  @Before
  fun setupViewModel() {
    userRepository = FakeUserRepository()
    profileViewModel = ProfileViewModel(userRepository)
  }

  @Test
  fun signupComplete_setsSignupCompleteEvent() {
    profileViewModel.finishSignup()

    val value = profileViewModel.signupCompletedEvent.getOrAwaitValue()

    assertThat(value.getContentIfNotHandled(), not(nullValue()))
  }

  @Test
  fun correctUsername_nextButtonVisible() {

    profileViewModel.onUsernameChanged("A")

    val value = profileViewModel.isUsernameCorrect.getOrAwaitValue()

    assertThat(value, `is`(true))

  }

  @Test
  fun emptyUsername_nextButtonNotVisible() {

    profileViewModel.onUsernameChanged("")

    val value = profileViewModel.isUsernameCorrect.getOrAwaitValue()

    assertThat(value, `is`(not(true)))

  }
}