package com.tuga.konum.view.ui.signup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.tuga.konum.R
import com.tuga.konum.compose.ViewModelFragment
import kotlinx.android.synthetic.main.fragment_password.*

class PasswordFragment : ViewModelFragment(), View.OnClickListener {
    private val args: PasswordFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val phoneNumber = args.phoneNumber

        Toast.makeText(activity, phoneNumber, Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnPasswordNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnPasswordNext -> {
                val password: String = textPassword.text.toString()
                val navController = v.findNavController()
                navController.navigate(
                    PasswordFragmentDirections.actionPasswordFragmentToEmailFragment(
                        password
                    )
                )
            }
        }
    }

}
