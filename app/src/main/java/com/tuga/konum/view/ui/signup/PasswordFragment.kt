package com.tuga.konum.view.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.binding.FragmentDataBindingComponent
import com.tuga.konum.databinding.FragmentPasswordBinding
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.util.autoCleared
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PasswordFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  var binding by autoCleared<FragmentPasswordBinding>()

  var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

  private val viewModel: SignupActivityViewModel by viewModels {
    viewModelFactory
  }

  private val args: PasswordFragmentArgs by navArgs()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_password,
      container,
      false,
      dataBindingComponent
    )

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    binding.viewModel = viewModel

    viewModel.navigateToEmailAction.observe(this, EventObserver { user ->
      navController()
        .navigate(PasswordFragmentDirections.actionPasswordFragmentToEmailFragment(user))
    })
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = this

    val user = args.user
    viewModel.setUser(user)

    binding.edtPassword.onTextChanged {
      viewModel.onPasswordChanged(it.toString())
    }
  }

  /**
   * Created to be able to override in tests
   */
  fun navController() = findNavController()

}
