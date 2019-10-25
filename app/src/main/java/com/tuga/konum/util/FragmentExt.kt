package com.tuga.konum.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuga.konum.KonumApplication
import com.tuga.konum.ViewModelFactory


fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    val repository = (requireContext().applicationContext as KonumApplication).userRepository
    return ViewModelProvider(this, ViewModelFactory(repository)).get(viewModelClass)
}
