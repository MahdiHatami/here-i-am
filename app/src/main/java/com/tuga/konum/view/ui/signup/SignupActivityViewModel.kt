package com.tuga.konum.view.ui.signup

import androidx.databinding.ObservableField
import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.repository.UserRepository
import timber.log.Timber

class SignupActivityViewModel
constructor(
  userRepository: UserRepository
) : DispatchViewModel() {
  var phoneNumber: ObservableField<String>? = null

  init {
    Timber.d("injection SignupActivityViewModel")
    phoneNumber = ObservableField("")
  }

  // if need to validate phone number must be implemented in this method
  fun onPhoneNumberChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
    if (s.length == 10) {
    }
  }
}
