package com.tuga.konum.domain

import com.tuga.konum.Resource

interface UseCase {
  interface ResourceUseCase<params : Params, T : Any> : UseCase {
    suspend fun executeAsync(params: params): Resource<T>
  }

  abstract class Params
}
