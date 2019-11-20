package com.tuga.konum.view.ui.signup.smsVerfication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.assertSnackbarMessage
import com.tuga.konum.R.string
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SmsViewModelTest {

  private lateinit var smsViewModel: SmsViewModel

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  // for testing liveData
  @get:Rule
  var instaExecuRule = InstantTaskExecutorRule()

  @Before
  fun setupViewModel() {
    smsViewModel = SmsViewModel()
  }

  @Test
  fun verifyOnClick_correctVerificationCode() {

    smsViewModel.apply {
      code1.value = "1"
      code2.value = "2"
      code3.value = "3"
      code4.value = "4"
    }

    val isValid = smsViewModel.isEnteredCodeValid()

    assertThat(isValid).isEqualTo(true)

  }

  @Test
  fun verifyOnClick_nullVerificationCodeError() {
    saveTaskAndAssertSnackbarError(null, null,null, null)
  }

  @Test
  fun verifyOnClick_emptyVerificationCodeError() {
    saveTaskAndAssertSnackbarError("", "", "", "")

    smsViewModel.apply {
    }

    val isValid = smsViewModel.isEnteredCodeValid()

    assertThat(isValid).isEqualTo(false)

  }

  @Test
  fun verifyOnClick_emptyVerificationCode() {

    smsViewModel.apply {
      code1.value = ""
      code2.value = ""
      code3.value = ""
      code4.value = ""
    }

    val isValid = smsViewModel.isEnteredCodeValid()

    assertThat(isValid).isEqualTo(false)

  }

  @Test
  fun verifyOnClick_inCompleteVerificationCode() {

    smsViewModel.apply {
      code1.value = "1"
      code2.value = ""
      code3.value = ""
      code4.value = ""
    }

    val isValid = smsViewModel.isEnteredCodeValid()

    assertThat(isValid).isEqualTo(false)

  }

  @Test
  fun verifyOnClick_enterAndRemoveFieldVerificationCode() {

    smsViewModel.apply {
      code1.value = "1"
      code2.value = "2"
      code3.value = "3"
      code4.value = ""
    }

    val isValid = smsViewModel.isEnteredCodeValid()

    assertThat(isValid).isEqualTo(false)

  }

  private fun saveTaskAndAssertSnackbarError(
    code1: String?,
    code2: String?,
    code3: String?,
    code4: String?
  ) {
    (smsViewModel).apply {
      this.code1.value = code1
      this.code2.value = code2
      this.code3.value = code3
      this.code4.value = code4
    }

    // When saving an incomplete task
    smsViewModel.verifyOnClick()

    // Then the snackbar shows an error
    assertSnackbarMessage(smsViewModel.snackbarMessage, string.sms_verification_required)
  }
}