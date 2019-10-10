package com.tuga.konum.view.ui.signup

import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.repository.UserRepository
import timber.log.Timber

class SignupActivityViewModel
constructor(
  userRepository: UserRepository
) : DispatchViewModel() {
  var phoneNumber: ObservableField<String>? = null
  var password: ObservableField<String>? = null
  var email: ObservableField<String>? = null
  var username: ObservableField<String>? = null

  init {
    Timber.d("injection SignupActivityViewModel")
    phoneNumber = ObservableField("")
    password = ObservableField("")
    email = ObservableField("")
    username = ObservableField("")
  }
}
