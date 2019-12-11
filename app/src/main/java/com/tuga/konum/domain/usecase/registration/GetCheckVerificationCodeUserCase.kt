package com.tuga.konum.domain.usecase.registration

import com.tuga.konum.base.Resource
import com.tuga.konum.domain.UseCase
import com.tuga.konum.domain.models.network.CheckVerificationCodeDto
import com.tuga.konum.domain.repository.UserRepository
import com.tuga.konum.domain.usecase.registration.GetCheckVerificationCodeUserCase.Params
import com.tuga.konum.util.ErrorFactory
import java.lang.Exception
import javax.inject.Inject

class GetCheckVerificationCodeUserCase @Inject constructor(
  private val userRepository: UserRepository,
  private val errorFactory: ErrorFactory
) : UseCase.ResourceUseCase<Params, Boolean> {
  override suspend fun executeAsync(params: Params): Resource<Boolean> {
    return try {
      val value = userRepository.checkVerificationCode(params.checkVerificationCodeDto)
      if (!value.result) return Resource.empty(errorFactory.createEmptyErrorMessage())
      Resource.success(value.result)
    } catch (e: Exception) {
      Resource.error(errorFactory.createApiErrorMessage(e))
    }
  }

  class Params(val checkVerificationCodeDto: CheckVerificationCodeDto) : UseCase.Params()

}
