package com.tuga.konum.models.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
  var username: String = "",
  var phoneNumber: String = "",
  var password: String = "",
  var email: String = ""
) : Parcelable
