package com.tuga.konum.view.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.tuga.konum.R
import com.tuga.konum.compose.ViewModelFragment
import com.tuga.konum.databinding.FragmentEmailBinding
import com.tuga.konum.models.entity.User
import kotlinx.android.synthetic.main.fragment_email.btnEmailNext
import kotlinx.android.synthetic.main.fragment_email.edtEmail
import org.jetbrains.anko.support.v4.toast
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class EmailFragment : ViewModelFragment(), View.OnClickListener {

  private val viewModel by viewModel<SignupActivityViewModel>()
  private lateinit var binding: FragmentEmailBinding
  private val args: EmailFragmentArgs by navArgs()
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
    binding = binding(inflater, R.layout.fragment_email, container)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    btnEmailNext.setOnClickListener(this)
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.btnEmailNext -> {
        val email = edtEmail.text.toString()
        user.email = email
        val navController = v.findNavController()
        navController.navigate(
          EmailFragmentDirections.actionEmailFragmentToProfileFragment(user)
        )
      }
    }
  }
}
