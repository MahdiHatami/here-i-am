package com.tuga.konum.view.ui.signup.circleOperation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuga.konum.Event
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

  private val _navigateToHomeAction = MutableLiveData<Event<Unit>>()
  val navigateToHomeAction: LiveData<Event<Unit>> = _navigateToHomeAction

}