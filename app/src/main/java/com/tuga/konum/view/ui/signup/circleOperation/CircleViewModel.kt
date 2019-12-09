package com.tuga.konum.view.ui.signup.circleOperation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuga.konum.Event
import com.tuga.konum.R.string
import com.tuga.konum.domain.usecase.circle.GetCreateCircleUseCase
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

  val liveDataMerger = MediatorLiveData<Boolean>()

  private val _snackbarText = MutableLiveData<Event<Int>>()
  val snackbarMessage: LiveData<Event<Int>> = _snackbarText


  private val _isCodeCorrect = MutableLiveData<Boolean>()
  val isCodeCorrect: LiveData<Boolean> = _isCodeCorrect

  private val _navigateToHomeAction = MutableLiveData<Event<Unit>>()
  val navigateToHomeAction: LiveData<Event<Unit>> = _navigateToHomeAction

  init {
    liveDataMerger.addSource(code1) {
      liveDataMerger.value = it.isNullOrEmpty()
    }
    liveDataMerger.addSource(code2) {
      liveDataMerger.value = it.isNullOrEmpty()
    }
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

  fun submitOnClick() {
    if (!isEnteredCodeValid()) {
      _snackbarText.value = Event(string.enter_circle_code_correctly)
      return
    }
  }

}
