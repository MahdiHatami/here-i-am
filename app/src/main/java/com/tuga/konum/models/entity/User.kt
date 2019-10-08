package com.tuga.konum.models.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = [("id")])
data class User(
  var phoneNumber: String,
  var password: String,
  var email: String,
  var token: String
) : Parcelable
