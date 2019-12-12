package com.tuga.konum.view.ui.signup.circleOperation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuga.konum.base.Event
import com.tuga.konum.R.string
import com.tuga.konum.domain.models.network.CreateCircleDto
import com.tuga.konum.domain.usecase.circle.GetCreateCircleUseCase
import com.tuga.konum.domain.usecase.circle.GetCreateCircleUseCase.Params
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    viewModelScope.launch(Dispatchers.IO) {
      val response = getCreateCircleUseCase.executeAsync(Params(CreateCircleDto()))
      // fire event to navigate
      if (response.data as Boolean)
        _navigateToHomeAction.postValue(Event(Unit))
      else
        _snackbarText.postValue(Event(string.sms_code_not_correct))
    }
  }

  fun createNewCircleOnClick() {
    _navigateToHomeAction.postValue(Event(Unit))
  }

}
