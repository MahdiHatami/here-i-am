package com.tuga.konum.view.ui.signup

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tuga.konum.R
import com.tuga.konum.compose.ViewModelFragment
import com.tuga.konum.databinding.FragmentPhoneNumberBinding
import kotlinx.android.synthetic.main.fragment_phone_number.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class PhoneNumberFragment : ViewModelFragment(), View.OnClickListener {

  private val viewModel by viewModel<SignupActivityViewModel>()

  private lateinit var binding: FragmentPhoneNumberBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = binding(inflater, R.layout.fragment_phone_number, container)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.btnNext -> {
        val phoneNumber = textPhoneNumber.text.toString()
        val navController = v.findNavController()
        navController.navigate(
            PhoneNumberFragmentDirections.actionPhoneNumberFragmentToPasswordFragment(
                phoneNumber
            )
        )
      }
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    observePhoneNumber()
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    btnNext.setOnClickListener(this)

    setupLegalDescriptionLink()
  }

  private fun setupLegalDescriptionLink() {
    val terms = getString(R.string.terms_of_service_text)
    val privacyPolicy = getString(R.string.privacy_policy_text)

    legalDescription.text = String.format(
        getString(R.string.terms_and_policy), terms, privacyPolicy
    )

    legalDescription.movementMethod = LinkMovementMethod.getInstance()

    val termsMatcher = Pattern.compile(terms)
    Linkify.addLinks(
        legalDescription, termsMatcher, getString(R.string.terms_link)
    )

    val privacyPolicyMatcher = Pattern.compile(privacyPolicy)
    Linkify.addLinks(legalDescription, privacyPolicyMatcher, getString(R.string.privacy_link))
  }

  private fun observePhoneNumber() {
    this.viewModel.phoneNumber.observe(this, androidx.lifecycle.Observer {

    })
  }
}
