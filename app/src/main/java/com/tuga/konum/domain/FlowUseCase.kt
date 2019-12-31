package com.tuga.konum.domain

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

/**
 * Base [Flow] use case meant to use in [CoroutineScopeOwner] implementations
 */
abstract class FlowUseCase<ARGS, T> {

  /**
   *  [Job] used to hold and cancel existing run of this use case
   */
  var job: Job? = null

  /**
   * Suspend function which should contain business logic
   */
  abstract fun build(args: ARGS): Flow<T>
}