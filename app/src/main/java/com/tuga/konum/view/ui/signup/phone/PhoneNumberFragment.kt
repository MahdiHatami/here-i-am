package com.tuga.konum.view.ui.signup.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentPhoneNumberBinding
import com.tuga.konum.extension.onTextChanged
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_phone_number.ccp
import kotlinx.android.synthetic.main.fragment_phone_number.edtPhoneNumber
import javax.inject.Inject

class PhoneNumberFragment : DaggerFragment() {

  private lateinit var viewDataBinding: FragmentPhoneNumberBinding

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel by viewModels<PhoneNumberViewModel> { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    viewDataBinding = FragmentPhoneNumberBinding.inflate(inflater, container, false).apply {
      this.viewModel = viewModel
    }
    return viewDataBinding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

    viewModel.navigateToPasswordAction.observe(this, EventObserver { user ->
      findNavController()
        .navigate(
          PhoneNumberFragmentDirections.actionPhoneNumberFragmentToPasswordFragment(
            user
          )
        )
    })
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    ccp.registerCarrierNumberEditText(edtPhoneNumber)

    edtPhoneNumber.onTextChanged {
      viewModel.onPhoneNumberChanged(it.toString())
    }
  }
}
