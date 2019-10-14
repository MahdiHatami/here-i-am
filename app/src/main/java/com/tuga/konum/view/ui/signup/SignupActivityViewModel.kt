package com.tuga.konum.view.ui.signup

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mlsdev.rximagepicker.RxImageConverters
import com.mlsdev.rximagepicker.RxImagePicker
import com.mlsdev.rximagepicker.Sources
import com.mlsdev.rximagepicker.Sources.GALLERY
import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.event.RequestStoragePermissionEvent
import com.tuga.konum.permission.PermissionStatus
import com.tuga.konum.permission.PermissionStatus.CAN_ASK_PERMISSION
import com.tuga.konum.permission.PermissionStatus.PERMISSION_GRANTED
import com.tuga.konum.repository.UserRepository
import org.greenrobot.eventbus.EventBus
import java.io.File

class SignupActivityViewModel
constructor(
  private val userRepository: UserRepository
) : DispatchViewModel() {
  private var storagePermissionStatus: PermissionStatus = CAN_ASK_PERMISSION
  private var cameraPermissionStatus: PermissionStatus = CAN_ASK_PERMISSION

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
      pickImage(GALLERY)
    }
  }

  fun imageCancelOnClick() {
    bottomSheetBehaviorState.set(BottomSheetBehavior.STATE_HIDDEN)
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

  fun setStoragePermissionStatus(newPermissionStatus: PermissionStatus) {
    if (storagePermissionStatus !== newPermissionStatus) {
      storagePermissionStatus = newPermissionStatus
      if (storagePermissionStatus === PERMISSION_GRANTED) {
        pickImage(GALLERY)
      } else {
        EventBus.getDefault().post(RequestStoragePermissionEvent())
      }
    }
  }

}
