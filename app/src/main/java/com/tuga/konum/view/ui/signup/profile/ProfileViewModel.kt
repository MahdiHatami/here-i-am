package com.tuga.konum.view.ui.signup.profile

import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mlsdev.rximagepicker.Sources
import com.mlsdev.rximagepicker.Sources.GALLERY
import com.tuga.konum.Event
import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.event.RequestGalleryImagePicker
import com.tuga.konum.event.RequestStoragePermissionEvent
import com.tuga.konum.models.entity.User
import com.tuga.konum.permission.PermissionStatus
import com.tuga.konum.permission.PermissionStatus.CAN_ASK_PERMISSION
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
  private val userRepository: UserRepository
) : ViewModel() {

  val TAG = "SignupActivityViewModel"

  private var storagePermissionStatus: PermissionStatus = CAN_ASK_PERMISSION
  private var cameraPermissionStatus: PermissionStatus = CAN_ASK_PERMISSION

  val bottomSheetBehaviorState = ObservableInt(BottomSheetBehavior.STATE_HIDDEN)

  private var user: User

  // two way data binding
  val username = MutableLiveData<String>()
  val userProfileImagePath = MutableLiveData<String>()

  // validation events
  private val _isUsernameCorrect = MutableLiveData<Boolean>()
  val isUsernameCorrect: LiveData<Boolean> = _isUsernameCorrect

  private val _signupCompleted = MutableLiveData<Event<Unit>>()
  val signupCompletedEvent: LiveData<Event<Unit>> = _signupCompleted


  init {
    user = User()
  }

  fun selectImage(state: Int) {
    when (state) {
      BottomSheetBehavior.STATE_EXPANDED ->
        bottomSheetBehaviorState.set(BottomSheetBehavior.STATE_COLLAPSED)
      BottomSheetBehavior.STATE_HIDDEN,
      BottomSheetBehavior.STATE_COLLAPSED ->
        bottomSheetBehaviorState.set(BottomSheetBehavior.STATE_EXPANDED)
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


  fun getUser(): User {
    return this.user
  }

  fun setUser(user: User) {
    this.user = user
    Timber.d(user.toString())
  }

  fun onUsernameChanged(username: String) {
    _isUsernameCorrect.value = username.length > 1
  }

  // Called on Profile next clicked
  fun finishSignup() {
    user.username = username.value.toString()
    viewModelScope.launch {
      userRepository.saveUser(user)
      _signupCompleted.value = Event(Unit)
    }
  }

}
