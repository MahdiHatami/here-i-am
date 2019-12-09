package com.tuga.konum.data.circle

import com.tuga.konum.api.ApiResponse
import com.tuga.konum.data.source.local.UserLocalDataSource
import com.tuga.konum.data.source.remote.UserRemoteDataSource
import com.tuga.konum.domain.models.network.CreateCircleDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface CircleRepository {
  suspend fun createCircle(createCircleDto: CreateCircleDto): ApiResponse<Boolean>
}

class DefaultCircleRepository @Inject constructor(
  private val userLocalDataSource: UserLocalDataSource,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CircleRepository {
  override suspend fun createCircle(createCircleDto: CreateCircleDto): ApiResponse<Boolean> {
    return TODO()
  }

}