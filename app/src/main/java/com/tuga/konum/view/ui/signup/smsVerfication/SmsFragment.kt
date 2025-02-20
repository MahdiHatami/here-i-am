package com.tuga.konum.view.ui.signup.smsVerfication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.tuga.konum.R
import com.tuga.konum.R.string
import com.tuga.konum.base.EventObserver
import com.tuga.konum.base.ext.observeWith
import com.tuga.konum.databinding.SmsFragmentBinding
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.extension.setupSnackbar
import com.tuga.konum.util.sms.Postman
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.support.v4.toast
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
    Timber.d("start onactivitycreated")

    setupEditText()
    setupSmsRetriever()
    setupSnackbar()

    viewModel.smsLiveData.observeWith(this) { smsUiState ->
      if (smsUiState.isError) {
        toast(string.resend_code_message)
      }
    }

    viewModel.navigateToPasswordAction.observe(viewLifecycleOwner,
      EventObserver { user ->
        findNavController()
          .navigate(SmsFragmentDirections.actionSmsFragmentToPasswordFragment(user))
      })

    viewModel.code1Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etVerificationCode1.requestFocus()
      })
    viewModel.code2Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etVerificationCode2.requestFocus()
      })
    viewModel.code3Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etVerificationCode3.requestFocus()
      })
    viewModel.code4Focus.observe(viewLifecycleOwner,
      EventObserver {
        binding.etVerificationCode4.requestFocus()
      })

    viewModel.startSmsReceiver()

  }

  private fun setupEditText() {
    binding.etVerificationCode1.onTextChanged {
      viewModel.onCode1Changed(it)
    }
    binding.etVerificationCode2.doOnTextChanged { text, _, count, after ->
      viewModel.onCode2Changed(text, count, after)
    }
    binding.etVerificationCode3.doOnTextChanged { text, _, count, after ->
      viewModel.onCode3Changed(text, count, after)
    }
    binding.etVerificationCode4.doOnTextChanged { _, _, count, after ->
      viewModel.onCode4Changed(count, after)
    }
  }

  private fun setupSnackbar() {
    view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
  }

  @SuppressLint("CheckResult")
  private fun setupSmsRetriever() {
    Postman(this)
      .getJustVerificationCode(true)
      .verificationCodeSize(4)
      .message()
      .subscribe { verificationCode ->
        viewModel.onVerificationCodeReceived(verificationCode)
      }
  }

}
