package com.tuga.konum.view.ui.signup.smsVerfication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tuga.konum.R
import com.tuga.konum.databinding.SmsFragmentBinding
import com.tuga.konum.util.sms.Postman
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.sms_fragment.etVerificationCode1
import kotlinx.android.synthetic.main.sms_fragment.etVerificationCode2
import kotlinx.android.synthetic.main.sms_fragment.etVerificationCode3
import kotlinx.android.synthetic.main.sms_fragment.etVerificationCode4
import javax.inject.Inject

class SmsFragment : DaggerFragment() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: SmsFragmentBinding
  private val viewModel by viewModels<SmsViewModel> { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.sms_fragment, container, false)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  @SuppressLint("CheckResult")
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    Postman(this)
      .getJustVerificationCode(true)
      .verificationCodeSize(4)
      .message()
      .subscribe { verificationCode ->
        viewModel.onVerificationCodeReceived(verificationCode)
      }

  }

}
