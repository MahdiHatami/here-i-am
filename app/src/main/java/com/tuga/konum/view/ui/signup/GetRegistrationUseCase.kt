package com.tuga.konum.view.ui.signup

import com.tuga.konum.Resource
import com.tuga.konum.UseCase
import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.models.network.CreateApplicantDto
import com.tuga.konum.util.ErrorFactory
import java.lang.Exception
import javax.inject.Inject

class GetRegistrationUseCase @Inject constructor(
  private val userRepository: UserRepository,
  private val errorFactory: ErrorFactory
) : UseCase.ResourceUseCase<CreateApplicantDto, Boolean> {

  override suspend fun createApplicant(dto: CreateApplicantDto): Resource<Boolean> {
    return try {
      val value = userRepository.getVerificationCode(dto)
      if (!value.result) return Resource.empty(errorFactory.createEmptyErrorMessage())
      Resource.success(value.result)
    } catch (e: Exception) {
      Resource.error(errorFactory.createApiErrorMessage(e))
    }
  }
}
