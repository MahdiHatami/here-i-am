package com.tuga.konum.view.ui.signup.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuga.konum.R
import com.tuga.konum.base.EventObserver
import com.tuga.konum.databinding.FragmentPhoneNumberBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PhoneNumberFragment : DaggerFragment() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: FragmentPhoneNumberBinding
  private val viewModel by viewModels<PhoneNumberViewModel> { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_number, container, false)
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    binding.lifecycleOwner = this
    binding.ccp.registerCarrierNumberEditText(binding.edtPhoneNumber)

    binding.ccp.setPhoneNumberValidityChangeListener {
      viewModel.onPhoneNumberChanged(binding.ccp.fullNumber, it)
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.navigateToPasswordAction.observe(viewLifecycleOwner,
      EventObserver { user ->
        findNavController()
          .navigate(
            PhoneNumberFragmentDirections.actionPhoneNumberFragmentToSmsFragment(
              user
            )
          )
      })
  }
}
