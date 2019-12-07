package com.tuga.konum.domain.usecase

import com.tuga.konum.Resource
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.models.network.CreateApplicantDto

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
