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

interface RegistrationUseCase {
  interface CreateApplicantUseCase<dto, T : Any> :
    RegistrationUseCase {
    suspend fun createApplicant(dto: CreateApplicantDto): Resource<T>
  }

  interface CheckVerificationCodeUseCase<dto, T : Any> :
    RegistrationUseCase {
    suspend fun checkVerificationCode(dto: CheckVerificationCodeDto): Resource<T>
  }

  interface CreateUserUserCase<user, result> :
    RegistrationUseCase {
    suspend fun createUser(user: User): Resource<result>
  }
}

class GetRegistrationUseCase @Inject constructor(
  private val userRepository: UserRepository,
  private val userMapper: UserMapper,
  private val errorFactory: ErrorFactory
) :
  RegistrationUseCase.CreateApplicantUseCase<CreateApplicantDto, Boolean>,
  RegistrationUseCase.CheckVerificationCodeUseCase<CheckVerificationCodeDto, Boolean>{

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
}
