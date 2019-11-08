package com.tuga.konum.view.ui.signup.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentPasswordBinding
import com.tuga.konum.extension.onTextChanged
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_password.edtPassword
import javax.inject.Inject

class PasswordFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel by viewModels<PasswordViewModel> { viewModelFactory }
  private lateinit var binding: FragmentPasswordBinding

  private val args: PasswordFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.navigateToEmailAction.observe(viewLifecycleOwner, EventObserver { user ->
      findNavController()
        .navigate(
          PasswordFragmentDirections.actionPasswordFragmentToEmailFragment(
            user
          )
        )
    })
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = this.viewLifecycleOwner

    val user = args.user
    viewModel.setUser(user)

    edtPassword.onTextChanged {
      viewModel.onPasswordChanged(it.toString())
    }
  }

}
