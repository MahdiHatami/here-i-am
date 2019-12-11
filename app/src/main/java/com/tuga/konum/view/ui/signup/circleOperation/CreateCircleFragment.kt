package com.tuga.konum.view.ui.signup.circleOperation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuga.konum.base.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentCreateCircleBinding
import dagger.android.support.DaggerFragment
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

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.navigateToHomeAction.observe(viewLifecycleOwner,
      EventObserver {
        findNavController().navigate(
          CreateCircleFragmentDirections.actionConfirmationFragmentToMainActivity()
        )
      })
  }

}
