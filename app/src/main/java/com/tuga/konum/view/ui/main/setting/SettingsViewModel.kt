package com.tuga.konum.view.ui.main.setting

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor() : ViewModel() {

  val darkMode = MediatorLiveData<Boolean>()
}
