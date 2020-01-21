package com.tuga.konum.view.ui.main.setting

import android.app.UiModeManager.MODE_NIGHT_YES
import android.os.Bundle
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentSettingsBinding
import com.tuga.konum.util.MaterialContainerTransition
import com.tuga.konum.extension.themeInterpolator
import com.tuga.konum.view.ui.signup.circleOperation.CircleViewModel_Factory
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import javax.inject.Inject

class SettingsFragment : DaggerFragment() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var binding: FragmentSettingsBinding

  private val viewModel by viewModels<SettingsViewModel> { viewModelFactory }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    prepareTransitions()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentSettingsBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    binding.run {
      closeIcon.setOnClickListener { findNavController().navigateUp() }
    }

    startTransitions()

    binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
      }
    }
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
