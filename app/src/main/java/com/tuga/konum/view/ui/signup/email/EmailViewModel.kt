package com.tuga.konum.view.ui.signup.email

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuga.konum.base.Event
import com.tuga.konum.domain.models.entity.User
import timber.log.Timber
import javax.inject.Inject

class EmailViewModel @Inject constructor() : ViewModel() {
  private var user = User()

  val email = MutableLiveData<String>()

  private val _isEmailCorrect = MutableLiveData<Boolean>()
  val isEmailCorrect: LiveData<Boolean> = _isEmailCorrect

  private val _navigateToProfileAction = MutableLiveData<Event<User>>()
  val navigateToProfileAction: LiveData<Event<User>> = _navigateToProfileAction

  fun setUser(user: User) {
    this.user = user
    Timber.d(user.toString())
  }

  fun onEmailChanged(email: String) {
    _isEmailCorrect.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  fun emailNextOnClick() {
    user.email = email.value.toString()
    _navigateToProfileAction.value = Event(user)
  }
}
