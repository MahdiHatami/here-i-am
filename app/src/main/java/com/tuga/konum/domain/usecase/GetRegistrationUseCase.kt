package com.tuga.konum.domain.usecase

import com.tuga.konum.Resource
import com.tuga.konum.domain.mappger.UserMapper
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.repository.UserRepository
import com.tuga.konum.domain.models.network.CreateApplicantDto
import com.tuga.konum.domain.models.network.CreateUserDto
import com.tuga.konum.util.ErrorFactory
import java.lang.Exception
import javax.inject.Inject

class GetRegistrationUseCase @Inject constructor(
  private val userRepository: UserRepository,
  private val userMapper: UserMapper,
  private val errorFactory: ErrorFactory
) :
  RegistrationUseCase.CreateApplicantUseCase<CreateApplicantDto, Boolean>,
  RegistrationUseCase.CheckVerificationCodeUseCase<CheckVerificationCodeDto, Boolean>,
  RegistrationUseCase.CreateUserUserCase<User, Boolean> {

  override suspend fun createApplicant(dto: CreateApplicantDto): Resource<Boolean> {
    return try {
      val value = userRepository.getVerificationCode(dto)
      if (!value.result) return Resource.empty(errorFactory.createEmptyErrorMessage())
      Resource.success(value.result)
    } catch (e: Exception) {
      Resource.error(errorFactory.createApiErrorMessage(e))
    }
  }

  override suspend fun checkVerificationCode(dto: CheckVerificationCodeDto): Resource<Boolean> {
    return try {
      val value = userRepository.checkVerificationCode(dto)
      if (!value.result) return Resource.empty(errorFactory.createEmptyErrorMessage())
      Resource.success(value.result)
    } catch (e: Exception) {
      Resource.error(errorFactory.createApiErrorMessage(e))
    }
  }

  override suspend fun createUser(user: User): Resource<Boolean> {
    return try {
      val dto = userMapper.invoke(user)
      val response = userRepository.createUser(dto)

      if (!response.result)
        return Resource.empty(errorFactory.createEmptyErrorMessage())

      userRepository.saveUser(user)
      Resource.success(response.result)

    } catch (e: Exception) {
      Resource.error(errorFactory.createApiErrorMessage(e))
    }
  }

}
