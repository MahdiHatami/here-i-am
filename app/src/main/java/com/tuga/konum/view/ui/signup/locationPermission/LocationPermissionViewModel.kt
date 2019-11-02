package com.tuga.konum.view.ui.signup.locationPermission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuga.konum.Event
import com.tuga.konum.models.entity.User
import com.tuga.konum.permission.PermissionStatus
import com.tuga.konum.permission.PermissionStatus.CAN_ASK_PERMISSION
import javax.inject.Inject

class LocationPermissionViewModel @Inject constructor(): ViewModel() {

  private var locationPermissionStatus: PermissionStatus = CAN_ASK_PERMISSION

  private val _requestLocationEvent = MutableLiveData<Event<Unit>>()
  val requestLocationPermissionEvent: LiveData<Event<Unit>> = _requestLocationEvent

  // navigation
  private val _navigateToCircle = MutableLiveData<Event<User>>()
  val navigateToCircleAction: LiveData<Event<User>> = _navigateToCircle


  fun setLocationPermissionStatus(newPermissionStatus: PermissionStatus) {
    if (locationPermissionStatus !== newPermissionStatus) {
      locationPermissionStatus = newPermissionStatus
    }
  }

  fun locationPermissionOnClick() {
    _requestLocationEvent.value = Event(Unit)
  }

}
