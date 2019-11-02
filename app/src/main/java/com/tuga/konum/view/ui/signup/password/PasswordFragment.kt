package com.tuga.konum.view.ui.signup.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentPasswordBinding
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.util.autoCleared
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_password.edtPassword
import javax.inject.Inject

class PasswordFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel by viewModels<PasswordViewModel> { viewModelFactory }

  private lateinit var viewDataBinding: FragmentPasswordBinding

  private val args: PasswordFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.fragment_password, container, false)
    viewDataBinding = FragmentPasswordBinding.bind(root).apply {
      this.viewModel = viewModel
    }
    return viewDataBinding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.navigateToEmailAction.observe(this, EventObserver { user ->
      findNavController()
        .navigate(
          PasswordFragmentDirections.actionPasswordFragmentToEmailFragment(
            user
          )
        )
    })
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

    val user = args.user
    viewModel.setUser(user)

    edtPassword.onTextChanged {
      viewModel.onPasswordChanged(it.toString())
    }
  }

}
