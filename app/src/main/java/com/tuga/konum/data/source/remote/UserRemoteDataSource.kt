package com.tuga.konum.data.source.remote

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.data.Result
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.models.entity.User
import com.tuga.konum.models.network.CreateApplicantDto
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
  private val konumService: KonumService
) : UserDataSource {

  override suspend fun getVerificationCode(createApplicantDto: CreateApplicantDto): ApiResponse<Boolean> =
    konumService.getVerificationCode(createApplicantDto)

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
