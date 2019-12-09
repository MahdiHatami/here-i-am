package com.tuga.konum.domain.usecase.registration

import com.tuga.konum.Resource
import com.tuga.konum.domain.UseCase
import com.tuga.konum.domain.models.network.CreateApplicantDto
import com.tuga.konum.domain.repository.UserRepository
import com.tuga.konum.domain.usecase.registration.GetCreateApplicantUserCase.Params
import com.tuga.konum.util.ErrorFactory
import javax.inject.Inject

class GetCreateApplicantUserCase @Inject constructor(
  private val userRepository: UserRepository,
  private val errorFactory: ErrorFactory
) : UseCase.ResourceUseCase<Params, Boolean> {

  override suspend fun executeAsync(params: Params): Resource<Boolean> {
    return try {
      val value = userRepository.getVerificationCode(params.createApplicantDto)
      if (!value.result) return Resource.empty(errorFactory.createEmptyErrorMessage())
      Resource.success(value.result)
    } catch (e: Exception) {
      Resource.error(errorFactory.createApiErrorMessage(e))
    }
  }

  class Params(val createApplicantDto: CreateApplicantDto) : UseCase.Params()

}
