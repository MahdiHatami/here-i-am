package com.tuga.konum.view.ui.signup.phone

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentPhoneNumberBinding
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.util.AppSignatureHelper
import com.tuga.konum.util.SMSBroadcastReceiver
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_phone_number.ccp
import kotlinx.android.synthetic.main.fragment_phone_number.edtPhoneNumber
import timber.log.Timber
import javax.inject.Inject

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
class PhoneNumberFragment : DaggerFragment() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private val smsBroadcastReceiver by lazy { SMSBroadcastReceiver() }

  private lateinit var binding: FragmentPhoneNumberBinding
  private val viewModel by viewModels<PhoneNumberViewModel> { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_number, container, false)
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    binding.lifecycleOwner = this
    ccp.registerCarrierNumberEditText(edtPhoneNumber)

    edtPhoneNumber.onTextChanged {
      viewModel.onPhoneNumberChanged(it.toString())
    }

    val helper = AppSignatureHelper(activity!!)
    val sig = helper.getApplicationSignature()
    sig.forEach {
      Timber.d(it)
    }

    val client = SmsRetriever.getClient(activity!!)
    val retriever = client.startSmsRetriever()
    retriever.addOnSuccessListener {
      val listener = object : SMSBroadcastReceiver.Listener {
        override fun onSMSReceived(otp: String) {
          // send data to server
          Timber.i("sms: $otp")
        }

        override fun onTimeOut() {
          Timber.d("timeout")
        }
      }
      smsBroadcastReceiver.injectListener(listener)
      activity?.registerReceiver(
        smsBroadcastReceiver,
        IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
      )
    }

    retriever.addOnFailureListener {
      Timber.d("onViewCreated: addOnFailureListener")
    }

  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.navigateToPasswordAction.observe(viewLifecycleOwner, EventObserver { user ->
      findNavController()
        .navigate(
          PhoneNumberFragmentDirections.actionPhoneNumberFragmentToPasswordFragment(
            user
          )
        )
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    activity?.unregisterReceiver(smsBroadcastReceiver)
  }

}
