package com.tuga.konum.data.source

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.data.Result
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.models.network.CreateApplicantDto
import com.tuga.konum.domain.models.network.CreateUserDto

/**
 * Main Entry point for user data
 */
interface UserDataSource {

  suspend fun getUser(): Result<User>

  suspend fun saveUser(user: User)

  suspend fun deleteUser(phoneNumber: String)

  suspend fun getVerificationCode(dto: CreateApplicantDto): ApiResponse<Boolean>

  suspend fun checkVerificationCode(dto: CheckVerificationCodeDto): ApiResponse<Boolean>

  suspend fun createUser(dto: CreateUserDto): ApiResponse<Boolean>
}
