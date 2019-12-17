package com.tuga.konum.view.ui.main.place

import android.os.Bundle
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentPlacesBinding
import com.tuga.konum.util.MaterialContainerTransition
import com.tuga.konum.view.material.themeInterpolator

class PlacesFragment : Fragment() {

  private lateinit var binding: FragmentPlacesBinding

  private lateinit var viewModel: PlacesViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    prepareTransitions()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentPlacesBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)

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
      setSharedElementViews(
        requireActivity().findViewById(R.id.bottom_app_bar),
        binding.placesCardView
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
