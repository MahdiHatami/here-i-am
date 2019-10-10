package com.tuga.konum.view.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.tuga.konum.R
import com.tuga.konum.compose.ViewModelFragment
import com.tuga.konum.databinding.FragmentPasswordBinding
import com.tuga.konum.models.entity.User
import kotlinx.android.synthetic.main.fragment_password.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class PasswordFragment : ViewModelFragment(), View.OnClickListener {

  private val viewModel by viewModel<SignupActivityViewModel>()
  private lateinit var binding: FragmentPasswordBinding
  private val args: PasswordFragmentArgs by navArgs()
  private lateinit var user: User

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    user = args.user
    Timber.d(user.toString())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = binding(inflater, R.layout.fragment_password, container)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    btnPasswordNext.setOnClickListener(this)
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btnPasswordNext -> {
        val password: String = edtPassword.text.toString()
        user.password = password
        val navController = v.findNavController()
        navController.navigate(
          PasswordFragmentDirections.actionPasswordFragmentToEmailFragment(user)
        )
      }
    }
  }

}
