package com.tuga.konum.binding

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuga.konum.domain.models.entity.User
import java.io.File

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
  view.visibility = if (visible) View.VISIBLE else View.GONE
}

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

@BindingAdapter("bindBackDrop")
fun bindBackDrop(view: ImageView, user: User) {
  Glide.with(view.context).load(user.image)
    .apply(RequestOptions().circleCrop())
    .into(view)
}

@BindingAdapter("imagePath")
fun loadImage(view: ImageView, imagePath: String?) {
  if (imagePath != null && imagePath.isNotEmpty()) {
    Glide.with(view.context).load(File(imagePath))
      .apply(RequestOptions().circleCrop())
      .into(view)
  }
}
