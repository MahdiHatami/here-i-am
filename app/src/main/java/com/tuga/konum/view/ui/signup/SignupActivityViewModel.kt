package com.tuga.konum.view.ui.signup

import androidx.lifecycle.MutableLiveData
import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.models.entity.User
import com.tuga.konum.repository.UserRepository
import timber.log.Timber

class SignupActivityViewModel
constructor(
  userRepository: UserRepository
) : DispatchViewModel() {
  var phoneNumber = MutableLiveData<String>()
  var password = MutableLiveData<String>()
  val userMutableLiveData: MutableLiveData<User>? = null

  init {
    Timber.d("injection SignupActivityViewModel")
  }
}
