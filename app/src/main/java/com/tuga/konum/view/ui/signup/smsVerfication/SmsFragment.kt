package com.tuga.konum.view.ui.signup.smsVerfication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.SmsFragmentBinding
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.extension.setupSnackbar
import com.tuga.konum.util.sms.Postman
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.sms_fragment.btnVerify
import kotlinx.android.synthetic.main.sms_fragment.etVerificationCode1
import kotlinx.android.synthetic.main.sms_fragment.etVerificationCode2
import kotlinx.android.synthetic.main.sms_fragment.etVerificationCode3
import kotlinx.android.synthetic.main.sms_fragment.etVerificationCode4
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

    setupEditText()
    setupSmsRetriever()
    setupSnackbar()
  }

  private fun setupEditText() {
    etVerificationCode1.onTextChanged {
      if (it.length == 1) {
        etVerificationCode2.requestFocus()
      }
    }
    etVerificationCode2.doOnTextChanged { text, start, count, after ->
      if (after < count) etVerificationCode1.requestFocus()
      if (text?.length == 1) etVerificationCode3.requestFocus()
    }
    etVerificationCode3.doOnTextChanged { text, start, count, after ->
      if (after < count) etVerificationCode2.requestFocus()
      if (text?.length == 1) etVerificationCode4.requestFocus()
    }
    etVerificationCode4.doOnTextChanged { text, start, count, after ->
      if (after < count) etVerificationCode3.requestFocus()
      if (text?.length == 1) btnVerify.requestFocus()
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
