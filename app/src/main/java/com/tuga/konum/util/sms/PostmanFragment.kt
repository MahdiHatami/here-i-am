package com.tuga.konum.util.sms

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.phone.SmsRetriever

internal class PostmanFragment : Fragment() {

  private val postmanBroadcastReceiver = PostmanBroadcastReceiver(this)

  private var message: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onStart() {
    super.onStart()
    startSmsUserConsent()
    registerToPostmanBroadcastReceiver()
  }

  override fun onStop() {
    super.onStop()
    unregisterToPostmanBroadcastReceiver()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    when (requestCode) {
      PostmanBroadcastReceiver.REQUEST_USER_CONSENT -> {
        if ((resultCode == Activity.RESULT_OK) && (data != null)) {
          val comingMessage = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
          message = comingMessage
        }
      }
    }
  }

  suspend fun getMessage(): String? = message

  private fun startSmsUserConsent() {
    context?.let {

      val senderNumber = arguments?.getString(KEY_SENDER_NUMBER)

      SmsRetriever.getClient(it)
        .startSmsUserConsent(senderNumber)
        .addOnSuccessListener { }
        .addOnFailureListener { }
    }
  }

  private fun registerToPostmanBroadcastReceiver() {
    activity?.registerReceiver(
      postmanBroadcastReceiver,
      IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
    )
  }

  private fun unregisterToPostmanBroadcastReceiver() {
    activity?.unregisterReceiver(postmanBroadcastReceiver)
  }

  companion object {

    const val TAG = "postman_fragment"

    private const val KEY_SENDER_NUMBER = "key_sender_number"

    fun newInstance(senderNumber: String? = null): PostmanFragment {
      return PostmanFragment().also { postmanFragment ->
        postmanFragment.arguments = Bundle().apply {
          putString(KEY_SENDER_NUMBER, senderNumber)
        }
      }
    }
  }
}