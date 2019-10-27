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
import com.tuga.konum.OpenForTesting
import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.data.source.UserRepositoryImpl
import com.tuga.konum.event.RequestGalleryImagePicker
import com.tuga.konum.event.RequestStoragePermissionEvent
import com.tuga.konum.models.entity.User
import com.tuga.konum.permission.PermissionStatus
import com.tuga.konum.permission.PermissionStatus.CAN_ASK_PERMISSION
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@OpenForTesting
class SignupActivityViewModel @Inject
constructor(
  private val userRepository: UserRepositoryImpl
) : DispatchViewModel() {

  private var storagePermissionStatus: PermissionStatus = CAN_ASK_PERMISSION
  private var cameraPermissionStatus: PermissionStatus = CAN_ASK_PERMISSION

  private var user: User

  val phoneNumber = MutableLiveData<String>()
  val firsname = MutableLiveData<String>()

  private val _isPhoneCorrect = MutableLiveData<Boolean>()
  val isPhoneCorrect: LiveData<Boolean> = _isPhoneCorrect

  val bottomSheetBehaviorState = ObservableInt(BottomSheetBehavior.STATE_HIDDEN)

  private val _navigateToPasswordAction = MutableLiveData<Event<User>>()
  val navigateToPasswordAction: LiveData<Event<User>> = _navigateToPasswordAction

  private val _signupCompleted = MutableLiveData<Event<Unit>>()
  val signupCompletedEvent: LiveData<Event<Unit>> = _signupCompleted

  var password: ObservableField<String>? = null
  var email: ObservableField<String>? = null
  var username: ObservableField<String>? = null
  var userProfileImagePath: MutableLiveData<String>

  init {
    user = User()
    password = ObservableField("")
    email = ObservableField("")
    username = ObservableField("")
    userProfileImagePath = MutableLiveData("")
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

  // Called on Profile next clicked
  fun finishSignup() {
    user.username = username.toString()
    viewModelScope.launch {
      userRepository.saveUser(user)
      _signupCompleted.value = Event(Unit)
    }
  }

  fun onPhoneNumberChanged(phone: String) {
    _isPhoneCorrect.value = phone.length == 10
  }

  fun setUser(user: User) {
    this.user = user
  }

  fun phoneNextOnClick() {
    _navigateToPasswordAction.value = Event(user)
  }

}
