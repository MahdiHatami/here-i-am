package com.tuga.konum.binding

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.File

@BindingAdapter("phoneNumberValidator")
fun bindPhoneNumberValidator(
  editText: EditText,
  phoneNumber: String?
) {
  val minimumLength = 10
  if (TextUtils.isEmpty(phoneNumber)) {
    editText.error = null
    return
  }
  if (editText.text.toString().length != minimumLength) {
    editText.error = "Phone Number must be minimum $minimumLength length"
  } else
    editText.error = null
}

@BindingAdapter(value = ["inputText", "errorText"], requireAll = false)
fun setInputWithError(
  editText: EditText,
  input: String,
  error: String
) {
  if (TextUtils.isEmpty(input))
    editText.error = error
  else
    editText.error = null
}

@BindingAdapter("bottomSheetBehaviorState")
fun setState(v: View, bottomSheetBehaviorState: Int) {
  val viewBottomSheetBehavior = BottomSheetBehavior.from(v)
  viewBottomSheetBehavior.state = bottomSheetBehaviorState
}

@BindingAdapter("imagePath")
fun loadImage(imageView: ImageView, imagePath: String?) {
  if (imagePath != null) {
    Glide.with(imageView).load(File(imagePath)).into(imageView)
  } else {
    imageView.setImageDrawable(null)
  }
}
