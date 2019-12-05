package com.tuga.konum.view.ui.signup.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuga.konum.Event
import com.tuga.konum.domain.models.entity.User
import timber.log.Timber
import javax.inject.Inject

class PasswordViewModel @Inject constructor() : ViewModel() {

  private var user: User = User()

  val password = MutableLiveData<String>()

  private val _isPasswordCorrect = MutableLiveData<Boolean>()
  val isPasswordCorrect: LiveData<Boolean> = _isPasswordCorrect

  private val _navigateToEmailAction = MutableLiveData<Event<User>>()
  val navigateToEmailAction: LiveData<Event<User>> = _navigateToEmailAction

  fun setUser(user: User) {
    this.user = user
    Timber.d(user.toString())
  }

  fun onPasswordChanged(password: String) {
    _isPasswordCorrect.value = password.length > 4
  }

  fun passwordNextOnClick() {
    user.password = password.value.toString()
    _navigateToEmailAction.value = Event(user)
  }
}
