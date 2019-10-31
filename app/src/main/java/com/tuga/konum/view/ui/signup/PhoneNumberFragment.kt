package com.tuga.konum.view.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.binding.FragmentDataBindingComponent
import com.tuga.konum.databinding.FragmentPhoneNumberBinding
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.util.autoCleared
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_phone_number.ccp
import kotlinx.android.synthetic.main.fragment_phone_number.edtPhoneNumber
import javax.inject.Inject

class PhoneNumberFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel by viewModels<SignupActivityViewModel> { viewModelFactory }

  private lateinit var viewDataBinding: FragmentPhoneNumberBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.fragment_phone_number, container, false)
    viewDataBinding = FragmentPhoneNumberBinding.bind(root).apply {
      this.viewModel = viewModel
    }
    // Set the lifecycle owner to the lifecycle of the view
    viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    return viewDataBinding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.navigateToPasswordAction.observe(this, EventObserver { user ->
      findNavController()
        .navigate(PhoneNumberFragmentDirections.actionPhoneNumberFragmentToPasswordFragment(user))
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
