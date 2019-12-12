package com.tuga.konum.view.ui.signup.smsVerfication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuga.konum.base.Event
import com.tuga.konum.R.string
import com.tuga.konum.base.Status.ERROR
import com.tuga.konum.base.Status.LOADING
import com.tuga.konum.base.Status.SUCCESS
import com.tuga.konum.coroutines.DefaultDispatcherProvider
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.models.network.CreateApplicantDto
import com.tuga.konum.domain.usecase.registration.GetCheckVerificationCodeUserCase
import com.tuga.konum.domain.usecase.registration.GetCheckVerificationCodeUserCase.Params
import com.tuga.konum.domain.usecase.registration.GetCreateApplicantUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SmsViewModel @Inject constructor(
  private val getCheckVerificationCodeUserCase: GetCheckVerificationCodeUserCase,
  private val getCreateApplicantUseCase: GetCreateApplicantUseCase
) : ViewModel() {

  private var user: User = User()

  val code1 = MutableLiveData<String>()
  val code2 = MutableLiveData<String>()
  val code3 = MutableLiveData<String>()
  val code4 = MutableLiveData<String>()

  private val _smsLiveData = MutableLiveData<SmsUiState>()
  val smsLiveData: LiveData<SmsUiState> get() = _smsLiveData

  private val getSmsUiState = _smsLiveData.value

  private val _navigateToPasswordAction = MutableLiveData<Event<User>>()
  val navigateToPasswordAction: LiveData<Event<User>> = _navigateToPasswordAction

  private val _snackbarText = MutableLiveData<Event<Int>>()
  val snackbarMessage: LiveData<Event<Int>> = _snackbarText

  private val _code1Focus = MutableLiveData<Event<Boolean>>()
  val code1Focus: LiveData<Event<Boolean>> = _code1Focus

  private val _code2Focus = MutableLiveData<Event<Boolean>>()
  val code2Focus: LiveData<Event<Boolean>> = _code2Focus

  private val _code3Focus = MutableLiveData<Event<Boolean>>()
  val code3Focus: LiveData<Event<Boolean>> = _code3Focus

  private val _code4Focus = MutableLiveData<Event<Boolean>>()
  val code4Focus: LiveData<Event<Boolean>> = _code4Focus

  init {
    Timber.d("init sms view model")
    _smsLiveData.value = SmsUiState()
  }

  fun startSmsReceiver() = viewModelScope.launch(DefaultDispatcherProvider.io()) {
    val dto = CreateApplicantDto(user.phoneNumber, "ASDFDFA")

    _smsLiveData.postValue(getSmsUiState?.copy(isLoading = true))
    val createApplicant =
      getCreateApplicantUseCase.executeAsync(GetCreateApplicantUseCase.Params(dto))
    _smsLiveData.postValue(
      getSmsUiState?.copy(
        data = createApplicant.data ?: false,
        isLoading = createApplicant.status == LOADING,
        isSuccess = createApplicant.status == SUCCESS,
        isError = createApplicant.status == ERROR,
        errorMessage = createApplicant.message
      )
    )
    if (getSmsUiState?.data != null && getSmsUiState.data)
      _snackbarText.postValue(Event(string.verification_code_will_be_send))
    else
      _snackbarText.postValue(Event(string.resend_code_message))
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
      _snackbarText.value =
        Event(string.sms_verification_required)
      return
    }

    viewModelScope.launch(Dispatchers.IO) {
      val code = code1.value + code2.value + code3.value + code4.value

      val dto = CheckVerificationCodeDto(user.phoneNumber, code)

      // send request for verification
      val response = getCheckVerificationCodeUserCase.executeAsync(Params(dto))

      // set user.phoneVerified
      user.phoneVerified = response.data != null

      // fire event to navigate
      if (user.phoneVerified)
        _navigateToPasswordAction.postValue(Event(user))
      else
        _snackbarText.postValue(Event(string.sms_code_not_correct))
    }
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

  fun onCode1Changed(text: CharSequence) {
    if (text.length == 1) _code2Focus.value = Event(true)
  }

  fun onCode2Changed(text: CharSequence?, count: Int, after: Int) {
    if (after < count) _code1Focus.value = Event(true)
    if (text?.length == 1) _code3Focus.value = Event(true)
  }

  fun onCode3Changed(text: CharSequence?, count: Int, after: Int) {
    if (after < count) _code2Focus.value = Event(true)
    if (text?.length == 1) _code4Focus.value = Event(true)
  }

  fun onCode4Changed(count: Int, after: Int) {
    if (after < count) _code3Focus.value = Event(true)
  }

  data class SmsUiState(
    val data: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
  )
}
