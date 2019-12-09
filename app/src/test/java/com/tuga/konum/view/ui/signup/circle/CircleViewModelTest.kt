package com.tuga.konum.view.ui.signup.circle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.R.string
import com.tuga.konum.assertSnackbarMessage
import com.tuga.konum.domain.usecase.circle.GetCreateCircleUseCase
import com.tuga.konum.view.ui.signup.circleOperation.CircleViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class CircleViewModelTest {
  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  // for testing liveData
  @get:Rule
  var instaExecuRule = InstantTaskExecutorRule()

  @MockK
  private lateinit var getCreateCircleUseCase: GetCreateCircleUseCase

  private lateinit var circleViewModel: CircleViewModel

  @Before
  fun setupViewModel() {
    MockKAnnotations.init(this)
    circleViewModel = CircleViewModel(getCreateCircleUseCase)
  }

  @Test
  fun submitOnClick_correctCode() {
    circleViewModel.apply {
      code1.value = "Z"
      code2.value = "A"
      code3.value = "Q"
      code4.value = "T"
      code5.value = "U"
      code6.value = "O"
    }

    val isValid = circleViewModel.isEnteredCodeValid()

    assertThat(isValid).isEqualTo(true)
  }

  @Test
  fun submitOnClick_nullCodeError() {
    assertSnackbarError(null, null, null, null, null, null)
  }

  @Test
  fun submitOnClick_emptyCodeError() {
    assertSnackbarError("", "", "", "", "", "")
  }

  @Test
  fun submitOnClick_unCompleteCodeError() {
    assertSnackbarError("Z", "X", "Y", "", "", "")
  }

  private fun assertSnackbarError(
    code1: String?,
    code2: String?,
    code3: String?,
    code4: String?,
    code5: String?,
    code6: String?
  ) {
    (circleViewModel).apply {
      this.code1.value = code1
      this.code2.value = code2
      this.code3.value = code3
      this.code4.value = code4
      this.code5.value = code5
      this.code6.value = code6
    }

    // When saving an incomplete task
    circleViewModel.submitOnClick()

    // Then the snackbar shows an error
    assertSnackbarMessage(circleViewModel.snackbarMessage, string.enter_circle_code_correctly)
  }
}
