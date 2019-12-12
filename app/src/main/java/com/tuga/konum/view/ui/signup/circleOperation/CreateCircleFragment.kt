package com.tuga.konum.view.ui.signup.circleOperation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tuga.konum.R
import com.tuga.konum.base.EventObserver
import com.tuga.konum.databinding.FragmentCreateCircleBinding
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.extension.setupSnackbar
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

    setupEditText()

    setupSnackbar()

    viewModel.navigateToHomeAction.observe(viewLifecycleOwner,
      EventObserver {
        findNavController().navigate(
          CreateCircleFragmentDirections.actionConfirmationFragmentToMainActivity()
        )
      })

    viewModel.code1Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etCircleCode1.requestFocus()
      })
    viewModel.code2Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etCircleCode2.requestFocus()
      })
    viewModel.code3Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etCircleCode3.requestFocus()
      })
    viewModel.code4Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etCircleCode4.requestFocus()
      })
    viewModel.code5Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etCircleCode5.requestFocus()
      })
    viewModel.code6Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etCircleCode6.requestFocus()
      })
  }

  private fun setupEditText() {
    binding.etCircleCode1.onTextChanged {
      viewModel.onCode1Changed(it)
    }
    binding.etCircleCode2.doOnTextChanged { text, _, count, after ->
      viewModel.onCode2Changed(text, count, after)
    }
    binding.etCircleCode3.doOnTextChanged { text, _, count, after ->
      viewModel.onCode3Changed(text, count, after)
    }
    binding.etCircleCode4.doOnTextChanged { text, _, count, after ->
      viewModel.onCode4Changed(text, count, after)
    }
    binding.etCircleCode5.doOnTextChanged { text, _, count, after ->
      viewModel.onCode5Changed(text, count, after)
    }
    binding.etCircleCode6.doOnTextChanged { _, _, count, after ->
      viewModel.onCode6Changed(count, after)
    }
  }

  private fun setupSnackbar() {
    view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
  }

}
