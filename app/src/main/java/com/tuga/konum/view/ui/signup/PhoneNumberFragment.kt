package com.tuga.konum.view.ui.signup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tuga.konum.R
import kotlinx.android.synthetic.main.fragment_phone_number.*

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
            R.id.btnNext -> navController.navigate(R.id.action_phoneNumberFragment_to_passwordFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnNext.setOnClickListener(this)
    }

}
