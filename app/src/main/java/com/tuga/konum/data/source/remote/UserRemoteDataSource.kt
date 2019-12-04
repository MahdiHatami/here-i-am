package com.tuga.konum.data.source.remote

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.data.Result
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.models.entity.User
import com.tuga.konum.models.network.BooleanResponse
import com.tuga.konum.models.network.CreateApplicantDto
import com.tuga.konum.models.network.UserDto
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
  private val userService: KonumService
) : UserDataSource {

  override suspend fun getVerificationCode(createApplicantDto: CreateApplicantDto): ApiResponse<BooleanResponse> {
    return userService.getVerificationCode(createApplicantDto)
  }

  override suspend fun deleteUser(phoneNumber: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun getUser(): Result<User> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun saveUser(user: User) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
