package com.tuga.konum.data.source.remote

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.data.Result
import com.tuga.konum.data.source.UserDataSource
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.models.network.CreateApplicantDto
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
  private val konumService: KonumService
) : UserDataSource {

  override suspend fun getVerificationCode(dto: CreateApplicantDto): ApiResponse<Boolean> =
    konumService.getVerificationCode(dto)

  override suspend fun checkVerificationCode(dto: CheckVerificationCodeDto): ApiResponse<Boolean> {
    return konumService.checkVerificationCode(dto)
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
