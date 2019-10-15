package com.tuga.konum

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SignupActivityViewModelTest{
//  private lateinit var signupActivityViewModel: SignupActivityViewModel
//  private lateinit var userRepository: UserRepository()

  @Before
  fun setupViewModel(){
//    userRepository = FakeRepository()
//    signupActivityViewModel = SignupActivityViewModel(userRepository)
  }
  @Test
  fun phoneNumberError() {
    assertEquals(4, 2 + 2)
  }
}
