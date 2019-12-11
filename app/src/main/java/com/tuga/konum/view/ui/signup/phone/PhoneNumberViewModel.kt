package com.tuga.konum.view.ui.signup.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuga.konum.base.Event
import com.tuga.konum.domain.models.entity.User
import timber.log.Timber
import javax.inject.Inject

class PhoneNumberViewModel
@Inject constructor() : ViewModel() {

  private var user: User = User()

  val phoneNumber = MutableLiveData<String>()

  private val _isPhoneCorrect = MutableLiveData<Boolean>()
  val isPhoneCorrect: LiveData<Boolean> = _isPhoneCorrect

  private val _navigateToPasswordAction = MutableLiveData<Event<User>>()
  val navigateToPasswordAction: LiveData<Event<User>> = _navigateToPasswordAction

  fun onPhoneNumberChanged(phone: String, isPhoneValid: Boolean) {
    user.phoneNumber = phone
    _isPhoneCorrect.value = isPhoneValid
  }

  fun isPhoneCorrect(phone: String): Boolean {
    return phone.length == 10
  }

  fun phoneNextOnClick() {
    user.phoneNumber = phoneNumber.value.toString()
    Timber.d(user.toString())
    _navigateToPasswordAction.value = Event(user)
  }
}
