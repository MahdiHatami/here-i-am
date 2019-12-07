package com.tuga.konum.domain.models.network

import com.google.gson.annotations.SerializedName

data class CreateUserDto(
  @SerializedName("Phone") val phone: String,
  @SerializedName("UserName") val userName: String,
  @SerializedName("Password") val password: String,
  @SerializedName("EmailAddress") val emailAddress: String,
  @SerializedName("FileContent") val image: String,
  @SerializedName("PhoneVerified") val phoneVerified: Boolean
)
