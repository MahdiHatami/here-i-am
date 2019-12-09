package com.tuga.konum.data.circle

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.data.source.remote.KonumService
import com.tuga.konum.domain.models.network.CreateCircleDto
import javax.inject.Inject

class CircleDataSource @Inject constructor(
  private val konumService: KonumService
) {

  suspend fun createCircle(dto: CreateCircleDto): ApiResponse<Boolean> {
    return konumService.createCircle(dto)
  }

}