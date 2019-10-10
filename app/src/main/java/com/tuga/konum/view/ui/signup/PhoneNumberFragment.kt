package com.tuga.konum.view.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tuga.konum.R
import com.tuga.konum.compose.ViewModelFragment
import com.tuga.konum.databinding.FragmentPhoneNumberBinding
import com.tuga.konum.models.entity.User
import kotlinx.android.synthetic.main.fragment_phone_number.*
import org.koin.android.viewmodel.ext.android.viewModel

class PhoneNumberFragment : ViewModelFragment(), View.OnClickListener {

  private val viewModel by viewModel<SignupActivityViewModel>()
  private lateinit var binding: FragmentPhoneNumberBinding
  private var user: User = User()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = binding(inflater, R.layout.fragment_phone_number, container)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    ccp.registerCarrierNumberEditText(edtPhoneNumber)
    btnNext.setOnClickListener(this)
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.btnNext -> {
        val phoneNumber = edtPhoneNumber.text.toString()
        user.phoneNumber = phoneNumber
        val navController = v.findNavController()
        navController.navigate(
          PhoneNumberFragmentDirections.actionPhoneNumberFragmentToPasswordFragment(user)
        )
      }
    }
  }
}
