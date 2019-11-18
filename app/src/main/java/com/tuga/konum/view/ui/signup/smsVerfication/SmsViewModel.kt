package com.tuga.konum.view.ui.signup.smsVerfication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SmsViewModel @Inject constructor() : ViewModel() {
  val code1 = MutableLiveData<String>()
  val code2 = MutableLiveData<String>()
  val code3 = MutableLiveData<String>()
  val code4 = MutableLiveData<String>()

  fun onVerificationCodeReceived(verificationCode: String?) {
    val chars = verificationCode?.toCharArray()

    if (chars?.size == 4) {
      code1.value = chars[0].toString()
      code2.value = chars[1].toString()
      code3.value = chars[2].toString()
      code4.value = chars[3].toString()
    }
  }
}
