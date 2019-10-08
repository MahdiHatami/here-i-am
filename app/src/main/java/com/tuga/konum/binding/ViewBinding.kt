package com.tuga.konum.binding

import androidx.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.EditText



@BindingAdapter("phoneNumberValidator")
fun passwordValidator(
  editText: EditText,
  password: String
) {
  val minimumLength = 5
  if (TextUtils.isEmpty(password)) {
    editText.error = null
    return
  }
  if (editText.text.toString().length < minimumLength) {
    editText.error = "Password must be minimum $minimumLength length"
  } else
    editText.error = null
}

