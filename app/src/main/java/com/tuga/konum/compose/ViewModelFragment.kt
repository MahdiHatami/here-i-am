package com.tuga.konum.compose

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class ViewModelFragment : Fragment() {

  protected inline fun <reified T : ViewDataBinding> binding(
    inflater: LayoutInflater,
    resId: Int,
    container: ViewGroup?
  ): T = DataBindingUtil.inflate(inflater, resId, container, false)
}
