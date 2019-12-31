package com.tuga.konum.view.ui.signup.circleOperation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuga.konum.R.string
import com.tuga.konum.base.Event
import com.tuga.konum.domain.models.network.CreateCircleDto
import com.tuga.konum.domain.usecase.circle.GetCreateCircleUseCase
import com.tuga.konum.domain.usecase.circle.GetCreateCircleUseCase.Params
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CircleViewModel @Inject constructor(
  private val getCreateCircleUseCase: GetCreateCircleUseCase
) : ViewModel() {

  val code1 = MutableLiveData<String>()
  val code2 = MutableLiveData<String>()
  val code3 = MutableLiveData<String>()
  val code4 = MutableLiveData<String>()
  val code5 = MutableLiveData<String>()
  val code6 = MutableLiveData<String>()

  private val _snackbarText = MutableLiveData<Event<Int>>()
  val snackbarMessage: LiveData<Event<Int>> = _snackbarText

  private val _isCodeCorrect = MutableLiveData<Boolean>()
  val isCodeCorrect: LiveData<Boolean> = _isCodeCorrect

  private val _navigateToHomeAction = MutableLiveData<Event<Unit>>()
  val navigateToHomeAction: LiveData<Event<Unit>> = _navigateToHomeAction

  private val _code1Focus = MutableLiveData<Event<Boolean>>()
  val code1Focus: LiveData<Event<Boolean>> = _code1Focus

  private val _code2Focus = MutableLiveData<Event<Boolean>>()
  val code2Focus: LiveData<Event<Boolean>> = _code2Focus

  private val _code3Focus = MutableLiveData<Event<Boolean>>()
  val code3Focus: LiveData<Event<Boolean>> = _code3Focus

  private val _code4Focus = MutableLiveData<Event<Boolean>>()
  val code4Focus: LiveData<Event<Boolean>> = _code4Focus

  private val _code5Focus = MutableLiveData<Event<Boolean>>()
  val code5Focus: LiveData<Event<Boolean>> = _code5Focus

  private val _code6Focus = MutableLiveData<Event<Boolean>>()
  val code6Focus: LiveData<Event<Boolean>> = _code6Focus

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  init {
    _isLoading.value = true
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

  fun onCode4Changed(text: CharSequence?, count: Int, after: Int) {
    if (after < count) _code3Focus.value = Event(true)
    if (text?.length == 1) _code5Focus.value = Event(true)
  }

  fun onCode5Changed(text: CharSequence?, count: Int, after: Int) {
    if (after < count) _code4Focus.value = Event(true)
    if (text?.length == 1) _code6Focus.value = Event(true)
  }

  fun onCode6Changed(count: Int, after: Int) {
    if (after < count) _code5Focus.value = Event(true)
  }

  fun isEnteredCodeValid(): Boolean {
    val c1 = code1.value
    val c2 = code2.value
    val c3 = code3.value
    val c4 = code4.value
    val c5 = code5.value
    val c6 = code6.value

    if (
      c1.isNullOrEmpty() || c2.isNullOrEmpty() ||
      c3.isNullOrEmpty() || c4.isNullOrEmpty() ||
      c5.isNullOrEmpty() || c6.isNullOrEmpty()
    ) {
      return false
    }
    return true
  }

  fun joinCircleOnClick() {
    if (!isEnteredCodeValid()) {
      _snackbarText.value =
        Event(string.enter_circle_code_correctly)
      return
    }

    _isLoading.value = false

    viewModelScope.launch(Dispatchers.IO) {
      val response = getCreateCircleUseCase.executeAsync(Params(CreateCircleDto()))
      Timber.d("$response")
      // fire event to navigate
      if (response as Boolean)
        _navigateToHomeAction.postValue(Event(Unit))
      else
        _snackbarText.postValue(Event(string.sms_code_not_correct))

      _isLoading.postValue(true)

    }
  }

  fun createNewCircleOnClick() {
    _navigateToHomeAction.postValue(Event(Unit))
  }

}
