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
import com.tuga.konum.EventObserver
import com.tuga.konum.OpenForTesting
import com.tuga.konum.R
import com.tuga.konum.binding.FragmentDataBindingComponent
import com.tuga.konum.databinding.FragmentPhoneNumberBinding
import com.tuga.konum.di.Injectable
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.models.entity.User
import com.tuga.konum.util.autoCleared
import kotlinx.android.synthetic.main.fragment_phone_number.ccp
import kotlinx.android.synthetic.main.fragment_phone_number.edtPhoneNumber
import javax.inject.Inject

@OpenForTesting
class PhoneNumberFragment : Fragment(), Injectable {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  var binding by autoCleared<FragmentPhoneNumberBinding>()

  var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

  private val viewModel: SignupActivityViewModel by viewModels {
    viewModelFactory
  }

  private var user: User = User()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_phone_number,
      container,
      false,
      dataBindingComponent
    )

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    binding.viewModel = viewModel

    viewModel.navigateToPasswordAction.observe(this, EventObserver { user ->
      navController()
        .navigate(PhoneNumberFragmentDirections.actionPhoneNumberFragmentToPasswordFragment(user))
    })
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    binding.lifecycleOwner = this
    ccp.registerCarrierNumberEditText(edtPhoneNumber)

    binding.edtPhoneNumber.onTextChanged {
      viewModel.onPhoneNumberChanged(it.toString())
    }
  }

  /**
   * Created to be able to override in tests
   */
  fun navController() = findNavController()
}
