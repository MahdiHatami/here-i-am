package com.tuga.konum.api.service

import androidx.annotation.BoolRes
import com.tuga.konum.api.ApiResponse
import com.tuga.konum.models.network.BooleanResponse
import com.tuga.konum.models.network.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

  @POST("/api/login/CreateApplicant")
  fun getVerificationCode(@Body userDto: UserDto): Call<ApiResponse<BooleanResponse>>

  @POST("/api/login/CheckVerificationCode")
  fun checkVerificationCode(@Body userDto: UserDto): Call<ApiResponse<String>>

}