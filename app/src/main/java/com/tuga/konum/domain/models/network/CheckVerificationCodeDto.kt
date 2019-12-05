package com.tuga.konum.domain.models.network

import com.google.gson.annotations.SerializedName

data class CheckVerificationCodeDto(
  @SerializedName("Phone") var phone: String,
  @SerializedName("VerificationCode") var verificationCode: String
)
