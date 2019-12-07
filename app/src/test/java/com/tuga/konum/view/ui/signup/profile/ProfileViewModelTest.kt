package com.tuga.konum.view.ui.signup.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.repository.UserRepository
import com.tuga.konum.domain.usecase.GetRegistrationUseCase
import com.tuga.konum.util.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

  // subject under test
  private lateinit var profileViewModel: ProfileViewModel

  @MockK
  private lateinit var getRegistrationUseCase: GetRegistrationUseCase

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  // for testing liveData
  @get:Rule
  var instaExecuRule = InstantTaskExecutorRule()

  private val user = User("5070933798", "mahdi", "123456")

  @Before
  fun setupViewModel() {
    MockKAnnotations.init(this)
    profileViewModel = ProfileViewModel(getRegistrationUseCase)
  }

  @Test
  fun signupComplete_setsSignupCompleteEvent() {
    val newUsername = "mahdi"
    profileViewModel.apply {
      username.value = newUsername
    }
    profileViewModel.finishSignup()

    val value = profileViewModel.signupCompletedEvent
    assertThat(value, not(nullValue()))
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
