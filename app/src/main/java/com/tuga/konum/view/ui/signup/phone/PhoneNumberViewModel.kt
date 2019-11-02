package com.tuga.konum.view.ui.signup.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuga.konum.Event
import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.data.source.UserRepositoryImpl
import com.tuga.konum.models.entity.User
import javax.inject.Inject

class PhoneNumberViewModel @Inject constructor() : ViewModel() {

  private var user: User = User()

  val phoneNumber = MutableLiveData<String>()

  private val _isPhoneCorrect = MutableLiveData<Boolean>()
  val isPhoneCorrect: LiveData<Boolean> = _isPhoneCorrect

  private val _navigateToPasswordAction = MutableLiveData<Event<User>>()
  val navigateToPasswordAction: LiveData<Event<User>> = _navigateToPasswordAction

  fun onPhoneNumberChanged(phone: String) {
    _isPhoneCorrect.value = phone.length == 10
  }

  fun phoneNextOnClick() {
    user.phoneNumber = phoneNumber.value.toString()
    _navigateToPasswordAction.value = Event(user)
  }
}
