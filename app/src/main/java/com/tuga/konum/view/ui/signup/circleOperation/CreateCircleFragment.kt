package com.tuga.konum.view.ui.signup.circleOperation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentCreateCircleBinding
import com.tuga.konum.di.KonumViewModelFactory
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class CreateCircleFragment : DaggerFragment() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: FragmentCreateCircleBinding
  private val viewModel by viewModels<CircleViewModel> { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_circle, container, false)
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = this
  }

}
