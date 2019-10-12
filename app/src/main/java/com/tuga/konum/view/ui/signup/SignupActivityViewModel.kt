package com.tuga.konum.view.ui.signup

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mlsdev.rximagepicker.Sources
import com.mlsdev.rximagepicker.Sources.GALLERY
import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.event.RequestStoragePermissionEvent
import com.tuga.konum.permission.PermissionStatus
import com.tuga.konum.permission.PermissionStatus.CAN_ASK_PERMISSION
import com.tuga.konum.permission.PermissionStatus.PERMISSION_GRANTED
import com.tuga.konum.repository.UserRepository
import org.greenrobot.eventbus.EventBus

class SignupActivityViewModel
constructor(
  userRepository: UserRepository
) : DispatchViewModel() {

  var storagePermissionStatus: PermissionStatus = CAN_ASK_PERMISSION
  var cameraPermissionStatus: PermissionStatus? = null

  var phoneNumber: ObservableField<String>? = null
  var password: ObservableField<String>? = null
  var email: ObservableField<String>? = null
  var username: ObservableField<String>? = null
  val bottomSheetBehaviorState = ObservableInt(BottomSheetBehavior.STATE_HIDDEN)

  init {
    phoneNumber = ObservableField("")
    password = ObservableField("")
    email = ObservableField("")
    username = ObservableField("")
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
      pickImage(Sources.GALLERY)
    }
  }

  private fun pickImage(source: Sources) {
    bottomSheetBehaviorState.set(BottomSheetBehavior.STATE_HIDDEN)
//    RxImagePicker.with(this).requestImage(source).flatMap { uri ->
//      RxImageConverters.uriToFile(this, uri, File.createTempFile("image", ".jpg"))
//    }.subscribe {
//      frameImageSection.hide()
//      imageSelected.show()
//      Glide.with(this).load(it).asBitmap().into(imageSelected)
//      mFile = it
//    }
  }

  companion object {
    fun setStoragePermissionStatus(
      signupActivityViewModel: SignupActivityViewModel,
      newPermissionStatus: PermissionStatus
    ) {
      if (signupActivityViewModel.storagePermissionStatus !== newPermissionStatus) {
        signupActivityViewModel.storagePermissionStatus = newPermissionStatus
        if (signupActivityViewModel.storagePermissionStatus === PERMISSION_GRANTED) {
          signupActivityViewModel.pickImage(GALLERY)
        }
        //      notifyChange()
      }
    }
  }

}
