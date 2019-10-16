package com.tuga.konum.models.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User @JvmOverloads constructor(
  @PrimaryKey @ColumnInfo(name = "phoneNumber") var phoneNumber: String = "",
  @ColumnInfo(name = "username") var username: String = "",
  @ColumnInfo(name = "password") var password: String = "",
  @ColumnInfo(name = "email") var email: String = "",
  @ColumnInfo(name = "image") var image: String = "",
  @ColumnInfo(name = "completed") var isCompleted: Boolean = false
) : Parcelable {

  val isActive
    get() = !isCompleted

  val isEmpty
    get() = phoneNumber.isEmpty()
}
