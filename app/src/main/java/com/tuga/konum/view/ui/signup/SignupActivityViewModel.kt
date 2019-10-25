package com.tuga.konum.view.ui.signup

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mlsdev.rximagepicker.Sources
import com.mlsdev.rximagepicker.Sources.GALLERY
import com.tuga.konum.Event
import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.event.RequestGalleryImagePicker
import com.tuga.konum.event.RequestStoragePermissionEvent
import com.tuga.konum.models.entity.User
import com.tuga.konum.permission.PermissionStatus
import com.tuga.konum.permission.PermissionStatus.CAN_ASK_PERMISSION
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class SignupActivityViewModel
constructor(
  private val userRepository: UserRepository
) : DispatchViewModel() {

  private var storagePermissionStatus: PermissionStatus = CAN_ASK_PERMISSION
  private var cameraPermissionStatus: PermissionStatus = CAN_ASK_PERMISSION

  private var user: User

  val bottomSheetBehaviorState = ObservableInt(BottomSheetBehavior.STATE_HIDDEN)

  val username = MutableLiveData<String>()

  private val _signupCompleted = MutableLiveData<Event<Unit>>()
  val signupCompletedEvent: LiveData<Event<Unit>> = _signupCompleted

  var phoneNumber: ObservableField<String>? = null
  var password: ObservableField<String>? = null
  var email: ObservableField<String>? = null
  //  var username: ObservableField<String>? = null
  var userProfileImagePath: MutableLiveData<String>

  init {
    user = User()
    phoneNumber = ObservableField("")
    password = ObservableField("")
    email = ObservableField("")
//    username = ObservableField("")
    userProfileImagePath = MutableLiveData("")
  }

  fun selectImage(state: Int) {
    when (state) {
      BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehaviorState.set(BottomSheetBehavior.STATE_COLLAPSED)
      BottomSheetBehavior.STATE_HIDDEN, BottomSheetBehavior.STATE_COLLAPSED -> bottomSheetBehaviorState.set(
        BottomSheetBehavior.STATE_EXPANDED
      )
    }
  }

  fun cameraOnClick() {
    pickImage(Sources.CAMERA)
  }

  fun galleryOnClick() {
    if (storagePermissionStatus == CAN_ASK_PERMISSION) {
      EventBus.getDefault().post(RequestStoragePermissionEvent())
    } else {
      pickImage(GALLERY)
    }
  }

  fun imageCancelOnClick() {
    bottomSheetBehaviorState.set(BottomSheetBehavior.STATE_HIDDEN)
  }

  private fun pickImage(source: Sources) {
    bottomSheetBehaviorState.set(BottomSheetBehavior.STATE_HIDDEN)
    EventBus.getDefault().post(RequestGalleryImagePicker(source))
  }

  fun setStoragePermissionStatus(newPermissionStatus: PermissionStatus) {
    if (storagePermissionStatus !== newPermissionStatus) {
      storagePermissionStatus = newPermissionStatus
    }
  }

  // Called on Profile next clicked
  fun finishSignup() {
    user.username = username.value!!
    viewModelScope.launch {
      userRepository.saveUser(user)
      _signupCompleted.value = Event(Unit)
    }
  }

  fun setUser(user: User) {
    this.user = user
  }

}
