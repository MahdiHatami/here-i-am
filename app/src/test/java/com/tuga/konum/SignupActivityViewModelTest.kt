package com.tuga.konum

import com.tuga.konum.repository.UserRepository
import com.tuga.konum.view.ui.signup.SignupActivityViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class SignupActivityViewModelTest{
  private lateinit var signupActivityViewModel: SignupActivityViewModel
//  private lateinit var userRepository: UserRepository()

  @Before
  fun setupViewModel(){
//    userRepository = FakeRepository()
    signupActivityViewModel = SignupActivityViewModel(userRepository)
  }
  @Test
  fun phoneNumberError() {
    assertEquals(4, 2 + 2)
  }
}
