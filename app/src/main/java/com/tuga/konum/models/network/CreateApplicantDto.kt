package com.tuga.konum.models.network

import com.google.gson.annotations.SerializedName

data class CreateApplicantDto(
  @SerializedName("Phone") var phone: String,
  @SerializedName("HashCode") var hashCode: String
)
