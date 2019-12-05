package com.tuga.konum.domain.models.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User @JvmOverloads constructor(
  @PrimaryKey
  @ColumnInfo(name = "phone_umber") var phoneNumber: String = "",
  @ColumnInfo(name = "username") var username: String = "",
  @ColumnInfo(name = "password") var password: String = "",
  @ColumnInfo(name = "email") var email: String = "",
  @ColumnInfo(name = "image") var image: String = "",
  @ColumnInfo(name = "phone_verified") var phoneVerified: Boolean = false,
  @ColumnInfo(name = "completed") var isCompleted: Boolean = false
) : Parcelable {

  val isEmpty
    get() = phoneNumber.isEmpty()
}
