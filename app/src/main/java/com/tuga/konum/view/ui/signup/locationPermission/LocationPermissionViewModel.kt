package com.tuga.konum.view.ui.signup.locationPermission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuga.konum.Event
import com.tuga.konum.permission.PermissionStatus
import com.tuga.konum.permission.PermissionStatus.CAN_ASK_PERMISSION
import com.tuga.konum.permission.PermissionStatus.PERMISSION_GRANTED
import timber.log.Timber
import javax.inject.Inject

class LocationPermissionViewModel @Inject constructor() : ViewModel() {

  private var locationPermissionStatus: PermissionStatus = CAN_ASK_PERMISSION

  private val _requestLocationEvent = MutableLiveData<Event<Unit>>()
  val requestLocationPermissionEvent: LiveData<Event<Unit>> = _requestLocationEvent

  // navigation
  private val _navigateToCircle = MutableLiveData<Event<Unit>>()
  val navigateToCircleAction: LiveData<Event<Unit>> = _navigateToCircle

  fun setLocationPermissionStatus(newPermissionStatus: PermissionStatus) {
    Timber.d(newPermissionStatus.toString())
    if (locationPermissionStatus !== newPermissionStatus) {
      locationPermissionStatus = newPermissionStatus
    }
  }

  fun locationPermissionOnClick() {
    if (locationPermissionStatus == PERMISSION_GRANTED) {
      _navigateToCircle.value = Event(Unit)
    } else {
      _requestLocationEvent.value = Event(Unit)
    }
  }

}
