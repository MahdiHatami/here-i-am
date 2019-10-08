package com.tuga.konum.view.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.tuga.konum.R

class EmailFragment : Fragment() {

  private val args: EmailFragmentArgs by navArgs()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val password: String = args.password

    Toast.makeText(activity, password, Toast.LENGTH_LONG)
        .show()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_email, container, false)
  }

}
