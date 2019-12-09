package com.tuga.konum.data.source.remote

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.models.network.CreateApplicantDto
import com.tuga.konum.domain.models.network.CreateCircleDto
import com.tuga.konum.domain.models.network.CreateUserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface KonumService {

  @POST("api/login/CreateApplicant")
  suspend fun getVerificationCode(@Body createApplicantDto: CreateApplicantDto): ApiResponse<Boolean>

  @POST("api/login/CheckVerificationCode")
  suspend fun checkVerificationCode(@Body dto: CheckVerificationCodeDto): ApiResponse<Boolean>

  @POST("api/User/CreateUser")
  suspend fun createUser(@Body dto: CreateUserDto): ApiResponse<Boolean>

  @POST("api/Circle/CreateCircle")
  suspend fun createCircle(@Body dto: CreateCircleDto): ApiResponse<Boolean>
}