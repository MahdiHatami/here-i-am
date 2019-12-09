package com.tuga.konum.domain.usecase.registration

import com.tuga.konum.Resource
import com.tuga.konum.domain.UseCase
import com.tuga.konum.domain.mappger.UserMapper
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.domain.repository.UserRepository
import com.tuga.konum.domain.usecase.registration.GetCreateUserUseCase.Params
import com.tuga.konum.util.ErrorFactory
import java.lang.Exception
import javax.inject.Inject

class GetCreateUserUseCase @Inject constructor(
  private val userRepository: UserRepository,
  private val userMapper: UserMapper,
  private val errorFactory: ErrorFactory
) : UseCase.ResourceUseCase<Params, Boolean> {
  override suspend fun executeAsync(params: Params): Resource<Boolean> {
    return try {
      val dto = userMapper.invoke(params.user)
      val response = userRepository.createUser(dto)

      if (!response.result)
        return Resource.empty(errorFactory.createEmptyErrorMessage())

      userRepository.saveUser(params.user)
      Resource.success(response.result)

    } catch (e: Exception) {
      Resource.error(errorFactory.createApiErrorMessage(e))
    }
  }

  class Params(val user: User) : UseCase.Params()

}