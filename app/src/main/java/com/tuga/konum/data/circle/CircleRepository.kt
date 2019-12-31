package com.tuga.konum.data.circle

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.base.Resource
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.domain.models.network.CreateCircleDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

interface CircleRepository {
  suspend fun createCircle(createCircleDto: CreateCircleDto): Boolean
}

class DefaultCircleRepository @Inject constructor(
  private val userLocalDataSource: UserLocalDataSource,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CircleRepository {
  override suspend fun createCircle(createCircleDto: CreateCircleDto): Boolean {
    delay(1000)
    return true
  }

}