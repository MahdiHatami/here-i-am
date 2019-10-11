package com.tuga.konum.view.ui.signup

import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mlsdev.rximagepicker.RxImageConverters
import com.mlsdev.rximagepicker.RxImagePicker
import com.mlsdev.rximagepicker.Sources
import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.repository.UserRepository
import timber.log.Timber
import java.io.File

class SignupActivityViewModel
constructor(
  userRepository: UserRepository
) : DispatchViewModel() {
  var phoneNumber: ObservableField<String>? = null
  var password: ObservableField<String>? = null
  var email: ObservableField<String>? = null
  var username: ObservableField<String>? = null

  init {
    Timber.d("injection SignupActivityViewModel")
    phoneNumber = ObservableField("")
    password = ObservableField("")
    email = ObservableField("")
    username = ObservableField("")
  }

  fun selectImage(){
    Timber.d("select image")
  }

  fun cameraOnClick() {
    pickImage(Sources.CAMERA)
  }

  fun galleryOnClick() {
    pickImage(Sources.GALLERY)
  }
  private fun pickImage(source: Sources) {
//    mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//    RxImagePicker.with(this).requestImage(source).flatMap { uri ->
//      RxImageConverters.uriToFile(this, uri, File.createTempFile("image", ".jpg"))
//    }.subscribe {
//      frameImageSection.hide()
//      imageSelected.show()
//      Glide.with(this).load(it).asBitmap().into(imageSelected)
//      mFile = it
//    }
  }

}
