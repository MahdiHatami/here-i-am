package com.tuga.konum.view.ui.main

import com.tuga.konum.compose.DispatchViewModel
import com.tuga.konum.repository.UserRepository

class MainViewModel constructor(
  private val userRepository: UserRepository
) : DispatchViewModel() {
  fun save() {
    print("called")
  }
}
