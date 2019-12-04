package com.tuga.konum

import com.tuga.konum.models.network.CheckVerificationCodeDto
import com.tuga.konum.models.network.CreateApplicantDto

interface RegistrationUseCase<T : Any> {
  suspend fun createApplicant(dto: CreateApplicantDto): Resource<T>
  suspend fun checkVerificationCode(dto: CheckVerificationCodeDto): Resource<T>

}
