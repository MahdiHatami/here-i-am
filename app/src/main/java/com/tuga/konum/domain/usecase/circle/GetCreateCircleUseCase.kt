package com.tuga.konum.domain.usecase.circle

import com.tuga.konum.base.Resource
import com.tuga.konum.data.circle.DefaultCircleRepository
import com.tuga.konum.domain.UseCase
import com.tuga.konum.domain.models.network.CreateCircleDto
import com.tuga.konum.domain.usecase.circle.GetCreateCircleUseCase.Params
import com.tuga.konum.util.ErrorFactory
import java.lang.Exception
import javax.inject.Inject

class GetCreateCircleUseCase @Inject constructor(
  private val circleRepository: DefaultCircleRepository,
  private val errorFactory: ErrorFactory
) : UseCase.ResourceUseCase<Params, Boolean> {
  override suspend fun executeAsync(params: Params): Resource<Boolean> {
    return try {
      val value = circleRepository.createCircle(params.createCircleDto)
      if (!value.result)
        return Resource.empty(errorFactory.createEmptyErrorMessage())
      Resource.success(value.result)
    } catch (e: Exception) {
      Resource.error(errorFactory.createApiErrorMessage(e))
    }
  }

  class Params(val createCircleDto: CreateCircleDto) : UseCase.Params()

}
