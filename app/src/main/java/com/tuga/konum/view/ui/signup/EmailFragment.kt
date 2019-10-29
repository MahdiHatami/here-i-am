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
import com.tuga.konum.OpenForTesting
import com.tuga.konum.R
import com.tuga.konum.binding.FragmentDataBindingComponent
import com.tuga.konum.databinding.FragmentEmailBinding
import com.tuga.konum.di.Injectable
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.util.autoCleared
import javax.inject.Inject

@OpenForTesting
class EmailFragment : Fragment(), Injectable {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  var binding by autoCleared<FragmentEmailBinding>()

  var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

  private val viewModel: SignupActivityViewModel by viewModels {
    viewModelFactory
  }

  private val args: PasswordFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_email,
      container,
      false,
      dataBindingComponent
    )

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    binding.viewModel = viewModel

    viewModel.navigateToProfileAction.observe(this, EventObserver { user ->
      navController()
        .navigate(EmailFragmentDirections.actionEmailFragmentToProfileFragment(user))
    })
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = this

    val user = args.user
    viewModel.setUser(user)

    binding.edtEmail.onTextChanged {
      viewModel.onEmailChanged(it.toString())
    }
  }

  fun navController() = findNavController()
}
