package com.tuga.konum

import androidx.lifecycle.LiveData
import com.tuga.konum.base.Event
import org.junit.Assert.assertEquals

fun assertLiveDataEventTriggered(
  liveData: LiveData<Event<String>>,
  taskId: String
) {
  val value = LiveDataTestUtil.getValue(liveData)
  assertEquals(value.getContentIfNotHandled(), taskId)
}

fun assertSnackbarMessage(snackbarLiveData: LiveData<Event<Int>>, messageId: Int) {
  val value: Event<Int> = LiveDataTestUtil.getValue(snackbarLiveData)
  assertEquals(value.getContentIfNotHandled(), messageId)
}
