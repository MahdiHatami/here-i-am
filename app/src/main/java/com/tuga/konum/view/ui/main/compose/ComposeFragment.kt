/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tuga.konum.view.ui.main.compose

import android.os.Bundle
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentComposeBinding
import com.tuga.konum.util.MaterialContainerTransition
import com.tuga.konum.extension.themeInterpolator

/**
 * A [Fragment] which allows for the composition of a new email.
 */
class ComposeFragment : Fragment() {

  private lateinit var binding: FragmentComposeBinding

//    private val args: ComposeFragmentArgs by navArgs()

  // The new email being composed.
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    prepareTransitions()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentComposeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.run {
      closeIcon.setOnClickListener { findNavController().navigateUp() }
    }

    startTransitions()
  }

  private fun prepareTransitions() {
    postponeEnterTransition()
  }

  private fun startTransitions() {
    binding.executePendingBindings()
    // Delay creating the enterTransition until after we have inflated this Fragment's binding
    // and are able to access the view to be transitioned to.
    enterTransition = MaterialContainerTransition(
      correctForZOrdering = true
    ).apply {
      // Manually add the Views to be shared since this is not a standard Fragment to Fragment
      // shared element transition.
      setSharedElementViews(
        requireActivity().findViewById(R.id.fab),
        binding.emailCardView
      )
      duration = resources.getInteger(R.integer.konum_motion_default_large).toLong()
      interpolator = requireContext().themeInterpolator(R.attr.motionInterpolatorPersistent)
    }
    returnTransition = Slide().apply {
      duration = resources.getInteger(R.integer.konum_motion_duration_medium).toLong()
      interpolator = requireContext().themeInterpolator(R.attr.motionInterpolatorOutgoing)
    }
    startPostponedEnterTransition()
  }
}
