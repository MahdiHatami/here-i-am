package com.tuga.konum.view.ui.signup.smsVerfication

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuga.konum.Event
import com.tuga.konum.R
import com.tuga.konum.models.entity.User
import com.tuga.konum.util.sms.Postman
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SmsViewModel @Inject constructor() : ViewModel() {

  private var user: User = User()

  val code1 = MutableLiveData<String>()
  val code2 = MutableLiveData<String>()
  val code3 = MutableLiveData<String>()
  val code4 = MutableLiveData<String>()

  private val _navigateToPasswordAction = MutableLiveData<Event<User>>()
  val navigateToPasswordAction: LiveData<Event<User>> = _navigateToPasswordAction

  private val _snackbarText = MutableLiveData<Event<Int>>()
  val snackbarMessage: LiveData<Event<Int>> = _snackbarText

  val liveDataManager = MediatorLiveData<String>()

  fun setupSms(fragment: Fragment) {
    viewModelScope.launch{
      val a = Postman(fragment)
        .getJustVerificationCode(true)
        .verificationCodeSize(4)
        .message()

      Timber.d("code is: $a")
    }
  }

  fun setUser(user: User) {
    this.user = user
    Timber.d(user.toString())
  }

  fun onVerificationCodeReceived(verificationCode: String?) {
    val chars = verificationCode?.toCharArray()

    if (chars?.size == 4) {
      code1.value = chars[0].toString()
      code2.value = chars[1].toString()
      code3.value = chars[2].toString()
      code4.value = chars[3].toString()
    }
  }

  fun verifyOnClick() {
    // check for validation
    if (!isEnteredCodeValid()) {
      _snackbarText.value = Event(R.string.sms_verification_required)
      return
    }

    // send request for verification

    // set user.phoneVerified
    user.phoneVerified = true
    // fire event to navigate
    _navigateToPasswordAction.value = Event(user)
  }

  fun isEnteredCodeValid(): Boolean {
    val c1 = code1.value
    val c2 = code2.value
    val c3 = code3.value
    val c4 = code4.value

    if (c1.isNullOrEmpty() || c2.isNullOrEmpty() || c3.isNullOrEmpty() || c4.isNullOrEmpty()) {
      return false
    }
    return true
  }
}
