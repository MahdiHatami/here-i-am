package com.tuga.konum.view.ui.signup.phone

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat;


@ExperimentalCoroutinesApi
class PhoneNumberViewModelTest {

  // subject under test
  private lateinit var phoneNumberViewModel: PhoneNumberViewModel

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  // for testing liveData
  @get:Rule
  var instaExecuRule = InstantTaskExecutorRule()

  @Before
  fun setupViewModel() {
    phoneNumberViewModel = PhoneNumberViewModel()
  }

  @Test
  fun wrongPhoneNumber_shouldReturnFalse() {
    phoneNumberViewModel.onPhoneNumberChanged("")

    val value = phoneNumberViewModel.isPhoneCorrect.getOrAwaitValue()

    assertThat(value, `is`(not(true)))
  }

  @Test
  fun correctPhone_returnTrue(){
    assertThat(phoneNumberViewModel.isPhoneCorrect("5070933798")).isTrue()
  }
  @Test
  fun wrongPhone_returnFalse(){
    assertThat(phoneNumberViewModel.isPhoneCorrect("3074")).isFalse()
  }

  @Test
  fun emptyPhone_returnFalse(){
    assertThat(phoneNumberViewModel.isPhoneCorrect("")).isFalse()
  }
}