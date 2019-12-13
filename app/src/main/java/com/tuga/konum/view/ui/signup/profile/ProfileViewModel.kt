package com.tuga.konum.view.ui.signup.profile

import android.app.Activity.RESULT_OK
import android.graphics.Bitmap
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mlsdev.rximagepicker.Sources
import com.mlsdev.rximagepicker.Sources.GALLERY
import com.theartofdev.edmodo.cropper.CropImage
import com.tuga.konum.R.string
import com.tuga.konum.base.Event
import com.tuga.konum.base.Resource
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.usecase.registration.GetCreateUserUseCase
import com.tuga.konum.domain.usecase.registration.GetCreateUserUseCase.Params
import com.tuga.konum.permission.PermissionStatus
import com.tuga.konum.permission.PermissionStatus.CAN_ASK_PERMISSION
import com.tuga.konum.util.BitmapResolver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
  private val getCreateUserUseCase: GetCreateUserUseCase
) : ViewModel() {

  val TAG = "SignupActivityViewModel"

  private var user: User

  private var storagePermissionStatus: PermissionStatus = CAN_ASK_PERMISSION
  private var cameraPermissionStatus: PermissionStatus = CAN_ASK_PERMISSION

  val bottomSheetBehaviorState = ObservableInt(BottomSheetBehavior.STATE_HIDDEN)

  // two way data binding
  val username = MutableLiveData<String>()
  val userProfileImagePath = MutableLiveData<String>()

  private val _requestStoragePermissionEvent = MutableLiveData<Event<Unit>>()
  val requestStoragePermissionEvent: LiveData<Event<Unit>> = _requestStoragePermissionEvent

  private val _requestGalleryImagePicker = MutableLiveData<Event<Unit>>()
  val requestGalleryImagePicker: LiveData<Event<Unit>> = _requestGalleryImagePicker

  // validation events
  private val _isUsernameCorrect = MutableLiveData<Boolean>()
  val isUsernameCorrect: LiveData<Boolean> = _isUsernameCorrect

  private val _signupCompleted = MutableLiveData<Event<Unit>>()
  val signupCompletedEvent: LiveData<Event<Unit>> = _signupCompleted

  private val _snackbarText = MutableLiveData<Event<Int>>()
  val snackbarMessage: LiveData<Event<Int>> = _snackbarText

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
      _requestStoragePermissionEvent.value = Event(Unit)
    } else {
      pickImage(GALLERY)
    }
  }

  fun imageCancelOnClick() {
    bottomSheetBehaviorState.set(BottomSheetBehavior.STATE_HIDDEN)
  }

  private fun pickImage(source: Sources) {
    bottomSheetBehaviorState.set(BottomSheetBehavior.STATE_HIDDEN)
    _requestGalleryImagePicker.value = Event(Unit)
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
    _isUsernameCorrect.value = username.isNotEmpty()
  }

  fun onActivityResultImagePick(
    resultCode: Int,
    path: String?,
    capturedImage: Bitmap
  ) {
    // duplicate check for result ok
    if (resultCode == RESULT_OK) {
      userProfileImagePath.value = path
      user.image = BitmapResolver.convertBitmapToBase64(capturedImage)
    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
      _snackbarText.value =
        Event(string.could_not_get_image)
    }
  }

  // Called on Profile next clicked
  fun finishSignup() = viewModelScope.launch(Dispatchers.IO) {
    user.username = username.value.toString()

    val response: Resource<Boolean> = getCreateUserUseCase.executeAsync(Params(user))

    if (response.data != null && response.data) {
      _snackbarText.postValue(Event(string.registration_successful))
      delay(1000)
      _signupCompleted.postValue(Event(Unit))
    } else {
      _snackbarText.postValue(Event(string.could_not_register_user_error))
    }
  }

}
