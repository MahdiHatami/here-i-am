package com.tuga.konum.view.ui.signup.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tuga.konum.base.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentEmailBinding
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.view.ui.signup.password.PasswordFragmentArgs
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_email.edtEmail
import javax.inject.Inject

class EmailFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel by viewModels<EmailViewModel> { viewModelFactory }

  private lateinit var binding: FragmentEmailBinding

  private val args: PasswordFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false)
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.navigateToProfileAction.observe(viewLifecycleOwner,
      EventObserver { user ->
        findNavController()
          .navigate(
            EmailFragmentDirections.actionEmailFragmentToProfileFragment(
              user
            )
          )
      })
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    binding.lifecycleOwner = this.viewLifecycleOwner
    val user = args.user
    viewModel.setUser(user)

    edtEmail.onTextChanged {
      viewModel.onEmailChanged(it.toString())
    }
  }
}
