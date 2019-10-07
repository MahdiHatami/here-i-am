package com.tuga.konum.view.ui.signup


import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tuga.konum.R
import kotlinx.android.synthetic.main.fragment_phone_number.*
import java.util.regex.Pattern

class PhoneNumberFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_phone_number, container, false)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnNext -> {
                val phoneNumber = textPhoneNumber.text.toString()
                val bundle = bundleOf("phoneNumber" to phoneNumber)
                navController.navigate(R.id.action_phoneNumberFragment_to_passwordFragment, bundle)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
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

}
