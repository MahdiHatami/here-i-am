package com.tuga.konum.view.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tuga.konum.R
import com.tuga.konum.view.ui.signup.SignupActivity

class MainFragment : Fragment() {

  companion object {
    fun newInstance() = MainFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.main_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    val intent = Intent(activity, SignupActivity::class.java)
    startActivity(intent)
  }

}
