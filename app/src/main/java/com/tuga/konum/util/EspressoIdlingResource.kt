package com.tuga.konum.util

object EspressoIdlingResource {
  private const val RESOURCE = "GLOBAL"

  @JvmField val countingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

  fun increment() {
    countingIdlingResource.increment()
  }

  fun decrement() {
    if (!countingIdlingResource.isIdleNow) {
      countingIdlingResource.decrement()
    }
  }
}
