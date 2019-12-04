package com.tuga.konum.api

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
  @SerializedName("result") val result: T,
  @SerializedName("targetUrl") val targetUrl: String,
  @SerializedName("success") val success: Boolean,
  @SerializedName("error") val error: String,
  @SerializedName("unAuthorizedRequest") val unAuthorizedRequest: Boolean
)