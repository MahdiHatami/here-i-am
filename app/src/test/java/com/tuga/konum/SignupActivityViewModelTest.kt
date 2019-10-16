package com.tuga.konum

import com.tuga.konum.data.source.Fake
import com.tuga.konum.view.ui.signup.SignupActivityViewModel
import org.greenrobot.eventbus.EventBus
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class SignupActivityViewModelTest {

  // subject under test
  private lateinit var signupActivityViewModel: SignupActivityViewModel

  private lateinit var userRepository: FakeUserRepository

  @Mock
  private lateinit var eventBus: EventBus

  @Before
  fun setupViewModel() {
    userRepository
    signupActivityViewModel = SignupActivityViewModel(userRepository)
  }

}
