package com.tuga.konum

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tuga.konum.data.source.FakeUserRepository
import com.tuga.konum.models.entity.User
import com.tuga.konum.permission.PermissionStatus
import com.tuga.konum.view.ui.signup.SignupActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.greenrobot.eventbus.EventBus
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify

class SignupActivityViewModelTest {

  // subject under test
  private lateinit var viewModel: SignupActivityViewModel

  // use a fake repository to be injected into viewModel
  private lateinit var userRepository: FakeUserRepository

  // Set the main coroutines dispatcher for unit testing.
  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  // Executes each task synchronously using Architecture Components.
  @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

  private val user = User("99999", "mahdi")


  @Before
  fun setupViewModel() {
    userRepository = FakeUserRepository()
    viewModel = SignupActivityViewModel(userRepository)
  }

  @Test
  fun testCanAskPermission() {
    viewModel.setStoragePermissionStatus(PermissionStatus.CAN_ASK_PERMISSION)

//    assertEquals(View.GONE, viewModel())
//    assertEquals(View.VISIBLE, viewModel.getPermissionVisibility())
//    assertEquals(R.string.permission_request, viewModel.getPermissionRequestText())
//    assertEquals(R.string.grant_access, viewModel.getPermissionButtonText())
//    verify<EventBus>(eventBus, never()).post(any(LoadPhotosEvent::class.java!!))
  }

}
