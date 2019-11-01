package com.tuga.konum.view.ui.signup.locationPermission

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentLocationPermissionBinding
import com.tuga.konum.permission.PermissionManager
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class LocationPermissionFragment : DaggerFragment() {

  private val REQUEST_CODE_ACCESS_FINE_LOCATION: Int = 100

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var viewDataBinding: FragmentLocationPermissionBinding

  private val viewModel by viewModels<LocationPermissionViewModel> { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.fragment_location_permission, container, false)
    viewDataBinding = FragmentLocationPermissionBinding.bind(root).apply {
      this.viewModel = viewModel
    }
    // Set the lifecycle owner to the lifecycle of the view
    viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    return viewDataBinding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.setLocationPermissionStatus(
      PermissionManager().getPermissionStatus(
        activity!!,
        android.Manifest.permission.ACCESS_FINE_LOCATION
      )
    )

    viewModel.requestLocationPermissionEvent.observe(this, EventObserver {
      ActivityCompat.requestPermissions(
        activity!!,
        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
        REQUEST_CODE_ACCESS_FINE_LOCATION
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
          toast("permission granted")
        } else {
          toast("permission denied")
        }
        viewModel.setLocationPermissionStatus(
          PermissionManager().getPermissionStatus(
            activity!!,
            android.Manifest.permission.ACCESS_FINE_LOCATION
          )
        )

      }

      else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
  }

}








