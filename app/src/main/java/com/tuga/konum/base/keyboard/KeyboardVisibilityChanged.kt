package com.tuga.konum.base.keyboard

data class KeyboardVisibilityChanged(
  val visible: Boolean,
  val contentHeight: Int,
  val contentHeightBeforeResize: Int
)
