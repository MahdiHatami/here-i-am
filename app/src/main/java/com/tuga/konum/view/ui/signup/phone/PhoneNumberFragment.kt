/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tuga.konum.view.ui.signup.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentPhoneNumberBinding
import com.tuga.konum.extension.onTextChanged
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_phone_number.ccp
import kotlinx.android.synthetic.main.fragment_phone_number.edtPhoneNumber
import javax.inject.Inject

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
class PhoneNumberFragment : DaggerFragment() {

  private lateinit var binding: FragmentPhoneNumberBinding

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel by viewModels<PhoneNumberViewModel> { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.fragment_phone_number, container, false)
    binding = FragmentPhoneNumberBinding.bind(root).apply {
      viewModel = viewModel
      lifecycleOwner = viewLifecycleOwner
    }
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    ccp.registerCarrierNumberEditText(edtPhoneNumber)

    edtPhoneNumber.onTextChanged {
      viewModel.onPhoneNumberChanged(it.toString())
    }

    viewModel.navigateToPasswordAction.observe(this, EventObserver { user ->
      findNavController()
        .navigate(
          PhoneNumberFragmentDirections.actionPhoneNumberFragmentToPasswordFragment(
            user
          )
        )
    })
  }

}
