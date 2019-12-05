package com.tuga.konum.domain.usecase

import com.tuga.konum.Resource
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.models.network.CreateApplicantDto

interface RegistrationUseCase {
  interface CreateApplicantUseCase<dto : CreateApplicantDto, T : Any> :
    RegistrationUseCase {
    suspend fun createApplicant(dto: CreateApplicantDto): Resource<T>
  }

  interface CheckVerificationCodeUseCase<dto : CheckVerificationCodeDto, T : Any> :
    RegistrationUseCase {
    suspend fun checkVerificationCode(dto: CheckVerificationCodeDto): Resource<T>
  }
}
