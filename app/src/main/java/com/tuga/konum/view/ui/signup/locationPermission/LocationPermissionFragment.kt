package com.tuga.konum.view.ui.signup.locationPermission

import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuga.konum.base.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentLocationPermissionBinding
import com.tuga.konum.permission.PermissionManager
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class LocationPermissionFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: FragmentLocationPermissionBinding

  private val viewModel by viewModels<LocationPermissionViewModel> { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_location_permission, container, false)
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.setLocationPermissionStatus(
      PermissionManager().getPermissionStatus(
        activity!!,
        android.Manifest.permission.ACCESS_FINE_LOCATION
      )
    )

    viewModel.requestLocationPermissionEvent.observe(viewLifecycleOwner,
      EventObserver {
        ActivityCompat.requestPermissions(
          activity!!,
          arrayOf(permission.ACCESS_FINE_LOCATION),
          REQUEST_CODE_ACCESS_FINE_LOCATION
        )
      })

    viewModel.navigateToCircleAction.observe(viewLifecycleOwner,
      EventObserver {
        findNavController()
          .navigate(
            LocationPermissionFragmentDirections
              .actionLocationPermissionFragmentToJoinCircleFragment()
          )
      })
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    when (requestCode) {
      REQUEST_CODE_ACCESS_FINE_LOCATION -> {
        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
          viewModel.setLocationPermissionStatus(
            PermissionManager().getPermissionStatus(
              activity!!,
              android.Manifest.permission.ACCESS_FINE_LOCATION
            )
          )
        } else {
          toast("permission denied")
        }

      }

      else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
  }

  companion object {
    private const val REQUEST_CODE_ACCESS_FINE_LOCATION: Int = 100
  }

}








