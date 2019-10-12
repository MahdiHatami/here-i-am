package com.tuga.konum.view.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.tuga.konum.R
import com.tuga.konum.compose.ViewModelFragment
import com.tuga.konum.databinding.FragmentProfileBinding
import com.tuga.konum.models.entity.User
import kotlinx.android.synthetic.main.fragment_profile.btnProfileNext
import kotlinx.android.synthetic.main.fragment_profile.edtUsername
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileFragment : ViewModelFragment(), OnClickListener {

  private val viewModel by viewModel<SignupActivityViewModel>()
  private lateinit var binding: FragmentProfileBinding
  private val args: ProfileFragmentArgs by navArgs()
  private var user: User = User()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    user = args?.user
    Timber.d(user.toString())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = binding(inflater, R.layout.fragment_profile, container)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    btnProfileNext.setOnClickListener(this)
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.btnProfileNext -> {
        val username = edtUsername.text.toString()
        user.username = username
        // todo create user profile and sync to server

        val navController = v.findNavController()
        navController.navigate(
          ProfileFragmentDirections.actionProfileFragmentToLocationPermissionFragment()
        )
      }
    }
  }



}
