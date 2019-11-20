package com.tuga.konum.view.ui.signup.smsVerfication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.SmsFragmentBinding
import com.tuga.konum.extension.setupSnackbar
import com.tuga.konum.util.sms.Postman
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class SmsFragment : DaggerFragment() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: SmsFragmentBinding
  private val viewModel by viewModels<SmsViewModel> { viewModelFactory }
  private val args: SmsFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.sms_fragment, container, false)
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = this

    val user = args.user
    viewModel.setUser(user)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.navigateToPasswordAction.observe(viewLifecycleOwner, EventObserver { user ->
      findNavController()
        .navigate(SmsFragmentDirections.actionSmsFragmentToPasswordFragment(user))
    })

    setupSmsRetriever()
    setupSnackbar()
  }

  private fun setupSnackbar() {
    view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
  }

  private fun setupSmsRetriever() {
    viewModel.setupSms(this)
  }

}
