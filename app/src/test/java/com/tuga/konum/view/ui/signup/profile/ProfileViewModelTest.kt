package com.tuga.konum.view.ui.signup.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.data.source.FakeUserRepository
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.util.getOrAwaitValue
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

  @Test
  fun saveNewUserToRepository_showSuccessMessageUi() {
    val newUsername = "mahdi"
    profileViewModel.apply {
      username.value = newUsername
    }
    profileViewModel.finishSignup()

    val newUser = userRepository.userServiceData.values.first()

    // Then
    Truth.assertThat(newUser.username).isEqualTo(newUsername)
  }
}
