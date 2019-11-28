package com.tuga.konum.data.source

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.models.entity.User
import com.tuga.konum.data.Result
import com.tuga.konum.models.network.BooleanResponse
import com.tuga.konum.models.network.UserDto

/**
 * Main Entry point for user data
 */
interface UserDataSource {

  suspend fun getUser(): Result<User>

  suspend fun saveUser(user: User)

  suspend fun deleteUser(phoneNumber: String)

  suspend fun getVerificationCode(userDto: UserDto): ApiResponse<BooleanResponse>
}
