package com.tuga.konum.api.client

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.api.async
import com.tuga.konum.api.service.AuthService
import com.tuga.konum.models.network.BooleanResponse
import com.tuga.konum.models.network.UserDto

class AuthClient(private val service: AuthService) {

  fun getVerificationCode(
    userDto: UserDto,
    onResult: (response: ApiResponse<BooleanResponse>) -> Unit
  ) {
    service.getVerificationCode(userDto).async(onResult)
  }
}